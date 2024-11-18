package com.acadify.presentation.kelolanilai

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.R

@Composable
fun KelolaNilai(
    mataKuliah: MataKuliah,
    onEditClick: (MataKuliah) -> Unit,
    onDeleteClick: (MataKuliah) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = mataKuliah.nama)
                Text(text = mataKuliah.nilai.toString())
                Text(text = mataKuliah.jumlahSKS.toString())
            }
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable(onClick = {
                            val updatedMataKuliah =
                                mataKuliah.copy(nilai = mataKuliah.nilai + 1) // Example update
                            onEditClick(updatedMataKuliah)
                        }),
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete",
                    modifier = Modifier.clickable(onClick = {
                        onDeleteClick(mataKuliah)
                    }),
                )
            }
        }
    }
}

@Preview
@Composable
fun KelolaNilaiPreview(modifier: Modifier = Modifier) {
    KelolaNilai(mataKuliah = MataKuliah("Matematika", 90f, 3f), onEditClick = {}, onDeleteClick = {})
}