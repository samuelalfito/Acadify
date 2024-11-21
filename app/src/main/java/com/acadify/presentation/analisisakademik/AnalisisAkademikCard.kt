package com.acadify.presentation.analisisakademik

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.PurpleBlue40

@Composable
fun AnalisisAcademikCard(
    mataKuliah: MataKuliah,
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
                Text(text = "SKS: ${mataKuliah.tambahNilai.jumlahSKS}", color = Color.White)
            }
            Text(
                text = "Nilai: ${mataKuliah.tambahNilai.nilai}",
                color = getColorForValue(mataKuliah.tambahNilai.nilai)
            )
        }
    }
}

fun getColorForValue(value: Float): Color {
    val green = Color(0xFF009A00) // Hijau
    val red = Color(0xFFFF0000) // Merah
    val fraction = value / 4f
    return lerp(red, green, fraction)
}

@Preview
@Composable
fun PreviewAnalisisAkademikCard() {
    AnalisisAcademikCard(
        mataKuliah = MataKuliah(
            tambahNilai = TambahNilai(
                nama = "Pemrograman Berbasis Objek",
                nilai = 90f,
                jumlahSKS = 3f
            )
        )
    )
}