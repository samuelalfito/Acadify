package com.acadify.presentation.simulasinilaiipk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.R
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.Blue40
import com.acadify.presentation.ui.theme.Green80
import com.acadify.presentation.ui.theme.PurpleBlue40

@Composable
fun SimulasiNilaiIPKCard(
    mataKuliah: MataKuliah,
    onEditClick: ((MataKuliah) -> Unit)? = null,
    onDeleteClick: ((MataKuliah) -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp),
        colors = CardDefaults.cardColors(
            if (onEditClick != null && onDeleteClick != null) {
                Green80
            } else Blue40
        ),
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
                Row {
                    Text(text = "Nilai: ${mataKuliah.tambahNilai.nilai}")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "SKS: ${mataKuliah.tambahNilai.jumlahSKS}")
                }
            }
            if (onEditClick != null && onDeleteClick != null) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = RoundedCornerShape(35),
                        colors = CardDefaults.cardColors(PurpleBlue40),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 10.dp)
                            .clickable(onClick = { onEditClick(mataKuliah) })
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.pencil),
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(8.dp),
                        )
                    }
                    Card(
                        shape = RoundedCornerShape(35),
                        colors = CardDefaults.cardColors(Color.Red),
                        modifier = Modifier.clickable(onClick = { onDeleteClick(mataKuliah) })
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.trash_can),
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SimulasiNilaiIPKCardPreview(modifier: Modifier = Modifier) {
    SimulasiNilaiIPKCard(mataKuliah = MataKuliah(TambahNilai("Matematika", 90f, 3f)),
        onEditClick = {},
        onDeleteClick = {})
}