package com.acadify.presentation.prediksiip

import androidx.compose.foundation.layout.Arrangement
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
import com.acadify.model.data.KomponenNilai
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.PrediksiIP
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.Blue40
import com.acadify.presentation.ui.theme.Grey40
import com.acadify.presentation.ui.theme.PurpleBlue40
import java.util.Locale

@Composable
fun PrediksiIPCard(mataKuliah: MataKuliah, prediksiIP: PrediksiIP) {
    Row {
        Card(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(Blue40),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "SKS: ${mataKuliah.tambahNilai.jumlahSKS}", color = Color.White)
                        Card(
                            colors = CardDefaults.cardColors(Grey40),
                            shape = RoundedCornerShape(
                                topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp
                            ),
                        ) {
                            Text(
                                String.format(
                                    Locale.getDefault(),
                                    "Kontribusi: %.2f",
                                    prediksiIP.nilaiAkhir.firstOrNull() ?: 0f
                                ),
                                modifier = Modifier.padding(8.dp),
                                color = Color.White,
                            )
                        }
                    }
                }
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
            komponenNilai = KomponenNilai(3f, 2f, 3f, 2f, 25f, 2f, 3f, 25f)
        ), prediksiIP = PrediksiIP(emptyList(), emptyList(), 0f)
    )
}