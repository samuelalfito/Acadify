package com.acadify.presentation.kelolanilai

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.R
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.PurpleBlue40

@Composable
fun KelolaNilaiCard(
    mataKuliah: MataKuliah,
    onEditClick: (MataKuliah) -> Unit,
    onDeleteClick: (MataKuliah) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(PurpleBlue40),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = mataKuliah.tambahNilai.nama, modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "Nilai mata kuliah: ${mataKuliah.tambahNilai.nilai}")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "Jumlah SKS: ${mataKuliah.tambahNilai.jumlahSKS}")
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Edit",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            onEditClick(mataKuliah)
                        },
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
    KelolaNilaiCard(mataKuliah = MataKuliah(TambahNilai("Matematika", 90f, 3f)),
        onEditClick = {},
        onDeleteClick = {})
}