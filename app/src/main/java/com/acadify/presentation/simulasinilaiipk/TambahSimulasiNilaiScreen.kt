package com.acadify.presentation.simulasinilaiipk

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai

@Composable
fun TambahSimulasiNilaiScreen(
    viewModel: SimulasiNilaiIPKViewModel,
    isTambahNilaiScreenVisible: MutableState<Boolean>,
) {
    var nama = remember { mutableStateOf("") }
    var nilai = remember { mutableStateOf("") }
    var jumlahSKS = remember { mutableStateOf("") }
    val context = LocalContext.current
    val showWarningDialog = remember { mutableStateOf(false) }
    
    if (showWarningDialog.value) {
        AlertDialog(onDismissRequest = { showWarningDialog.value = false },
            title = { Text("Peringatan") },
            text = { Text("Apakah anda yakin untuk meninggalkan halaman?") },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color.Cyan),
                    onClick = {
                        showWarningDialog.value = false
                        isTambahNilaiScreenVisible.value = false
                    }) {
                    Text("Iya", color = Color.Cyan)
                }
            },
            dismissButton = {
                Button(onClick = { showWarningDialog.value = false }) {
                    Text("Tidak")
                }
            })
    }
    
    AlertDialog(onDismissRequest = {
        showWarningDialog.value = true
    }, title = { Text("Tambah Nilai") }, text = {
        Column(modifier = Modifier.padding(8.dp)) {
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
        }
    }, confirmButton = {
        Button(onClick = {
            if (nama.value.isEmpty() || nilai.value.isEmpty() || jumlahSKS.value.isEmpty()) {
                Toast.makeText(
                    context, "Mohon isi semua field yang ada", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (nama.value.any { it.isDigit() } || nilai.value.toFloatOrNull() == null || jumlahSKS.value.toFloatOrNull() == null) {
                Toast.makeText(
                    context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (nilai.value.toFloat() !in 0.0..4.0) {
                Toast.makeText(
                    context, "Nilai dalam skala 4", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            viewModel.addMataKuliah(
                MataKuliah(
                    "",
                    TambahNilai(
                        nama = nama.value,
                        nilai = nilai.value.toFloat(),
                        jumlahSKS = jumlahSKS.value.toFloat()
                    ), null
                )
            )
            isTambahNilaiScreenVisible.value = false
            Toast.makeText(context, "Nilai berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }) {
            Text("Tambah")
        }
    }, dismissButton = {
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            onClick = { isTambahNilaiScreenVisible.value = false }) {
            Text("Batal", color = MaterialTheme.colorScheme.primary)
        }
    })
}

@Preview
@Composable
fun TambahSimulasiNilaiScreenPreview() {
    TambahSimulasiNilaiScreen(SimulasiNilaiIPKViewModel(), mutableStateOf(true))
//    TambahNilaiScreen(mutableStateOf(true))
}
