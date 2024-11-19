package com.acadify.presentation.kelolanilai

import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.R

@Composable
fun TambahNilaiScreen(
    viewModel: KelolaNilaiViewModel,
    isTambahNilaiScreenVisible: MutableState<Boolean>,
) {
    var nama = remember { mutableStateOf("") }
    var nilai = remember { mutableStateOf("") }
    var jumlahSKS = remember { mutableStateOf("") }
    Card {
        Column(modifier = Modifier.padding(8.dp)) {
            Card(
                modifier = Modifier.align(Alignment.End),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Red),
                onClick = {
                    isTambahNilaiScreenVisible.value = false
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            OutlinedTextField(
                value = nama.value,
                onValueChange = { newValue -> nama.value = newValue },
                placeholder = { Text("Nama") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
            OutlinedTextField(
                value = nilai.value,
                onValueChange = { newValue -> nilai.value = newValue },
                placeholder = { Text("Nilai") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
            OutlinedTextField(
                value = jumlahSKS.value,
                onValueChange = { newValue -> jumlahSKS.value = newValue },
                placeholder = { Text("jumlahSKS") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.addMataKuliah(
                        MataKuliah(
                            TambahNilai(
                                nama = nama.value,
                                nilai = nilai.value.toFloat(),
                                jumlahSKS = jumlahSKS.value.toFloat()
                            )
                        )
                    )
                    isTambahNilaiScreenVisible.value = false
                }) {
                Text("Tambah")
            }
        }
    }
}

@Preview
@Composable
fun TambahNilaiScreenPreview() {
    TambahNilaiScreen(KelolaNilaiViewModel(), mutableStateOf(true))
}