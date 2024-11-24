package com.acadify.presentation.prediksiip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.KomponenNilai
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.PrediksiIP
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.Blue40
import com.acadify.presentation.ui.theme.Grey40
import com.acadify.presentation.ui.theme.PurpleBlue40
import com.acadify.presentation.ui.theme.PurpleBlue80
import java.util.Locale

@Composable
fun PrediksiIPCard(mataKuliah: MataKuliah, prediksiIP: PrediksiIP) {
    Row {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp, bottom = 10.dp, start = 30.dp),
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
            }
        }
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically),
            colors = CardDefaults.cardColors(Grey40),
            shape = RoundedCornerShape(
                topStart = 0.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 8.dp
            ),
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    String.format(
                        Locale.getDefault(),
                        "Prediksi\nNilai:\n%.2f",
                        prediksiIP.nilaiAkhir.firstOrNull() ?: 0f
                    ),
                    color = Color.White,
                )
            }
        }
    }
}

@Preview
@Composable
fun PrediksiIPCardPreview() {
    PrediksiIPCard(
        mataKuliah = MataKuliah(
            tambahNilai = TambahNilai("Matematika", 90f, 3f),
            komponenNilai = KomponenNilai(3f, 2f, 3f, 2f, 25, 2, 3, 25)
        ), prediksiIP = PrediksiIP(emptyList(), emptyList(), 0f)
    )
}