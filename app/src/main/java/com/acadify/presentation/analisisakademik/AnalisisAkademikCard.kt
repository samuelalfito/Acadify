package com.acadify.presentation.analisisakademik

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.Blue40
import com.acadify.presentation.ui.theme.PurpleBlue40

@Composable
fun AnalisisAcademikCard(
    mataKuliah: MataKuliah,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp),
        colors = CardDefaults.cardColors(Blue40),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(PurpleBlue40)
                ) {
                    Text(
                        text = mataKuliah.tambahNilai.nama,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "SKS: ${mataKuliah.tambahNilai.jumlahSKS}", color = Color.White)
            }
            Box(contentAlignment = Alignment.CenterEnd) {
                Card(
                    modifier = Modifier.padding(start = 20.dp),
                    shape = RoundedCornerShape(35),
                    colors = CardDefaults.cardColors(getColorForValue(mataKuliah.tambahNilai.nilai))
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "Nilai: ${mataKuliah.tambahNilai.nilai}",
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun getColorForValue(value: Float): Color {
    return when {
        value >= 4f -> Color(0xFF007D00)
        value >= 3f -> Color(0xFF50BF50)
        value >= 2f -> Color(0xFFFFFF00)
        value >= 1f -> Color(0xFFFFA500)
        else -> Color(0xFFEA0000)
    }
}

@Preview
@Composable
fun PreviewAnalisisAkademikCard() {
    AnalisisAcademikCard(
        mataKuliah = MataKuliah(
            tambahNilai = TambahNilai(
                nama = "Pemrograman Berbasis Objek", nilai = 3f, jumlahSKS = 3f
            )
        )
    )
}