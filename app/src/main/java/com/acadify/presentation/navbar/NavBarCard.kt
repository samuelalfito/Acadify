package com.acadify.presentation.navbar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.presentation.ui.theme.Purple40

@Composable
fun NavBarCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = {
                if (!isSelected) onClick()
            })
            .then(
                if (isSelected) Modifier.border(
                    2.dp, Color.White, shape = RoundedCornerShape(10.dp)
                ) else Modifier
            ),
        colors = if (isSelected) CardDefaults.cardColors(Purple40) else CardDefaults.cardColors()
    ) {
        Text(
            title, modifier = Modifier
                .padding(10.dp)
                .height(20.dp), color = Color.White
        )
    }
}

@Preview
@Composable
fun NavBarCardPreview() {
    NavBarCard(title = "Kelola Nilai", isSelected = false, onClick = {})
}