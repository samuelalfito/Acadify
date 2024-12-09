package com.acadify.presentation.simulasinilaiipk

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun EditSimulasiNilaiScreen(
    viewModel: SimulasiNilaiIPKViewModel,
    mataKuliah: MataKuliah,
    isEditNilaiScreenVisible: MutableState<Boolean>,
) {
    var nama = remember { mutableStateOf(mataKuliah.tambahNilai.nama) }
    var nilai = remember { mutableStateOf(mataKuliah.tambahNilai.nilai.toString()) }
    var jumlahSKS = remember { mutableStateOf(mataKuliah.tambahNilai.jumlahSKS.toString()) }
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
                        isEditNilaiScreenVisible.value = false
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
    }, title = { Text("Edit Nilai") }, text = {
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
            if (nama.value.any { it.isDigit() } || nilai.value.toFloatOrNull() == null || jumlahSKS.value.toFloatOrNull() == null) {
                Toast.makeText(
                    context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (nama.value.isEmpty() || nilai.value.isEmpty() || jumlahSKS.value.isEmpty()) {
                return@Button
            }
            if (nilai.value.toFloat() !in 0.0..4.0) {
                Toast.makeText(
                    context, "Nilai dalam skala 4", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            viewModel.updateMataKuliah(
                oldValue = mataKuliah, newValue = MataKuliah(
                    mataKuliah.id, TambahNilai(
                        nama = nama.value,
                        nilai = nilai.value.toFloat(),
                        jumlahSKS = jumlahSKS.value.toFloat()
                    ), null
                )
            )
            isEditNilaiScreenVisible.value = false
            Toast.makeText(context, "Nilai berhasil diubah", Toast.LENGTH_SHORT).show()
        }) {
            Text("Simpan")
        }
    }, dismissButton = {
        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color.Cyan),
            onClick = { isEditNilaiScreenVisible.value = false }) {
            Text("Batal", color = Color.Cyan)
        }
    })
}

@Preview
@Composable
fun EditSimulasiNilaiScreenPreview() {
    EditSimulasiNilaiScreen(
        viewModel = SimulasiNilaiIPKViewModel(), mataKuliah = MataKuliah(
            tambahNilai = TambahNilai(
                nama = "Pemrograman Berorientasi Objek", nilai = 4.0f, jumlahSKS = 3.0f
            )
        ), isEditNilaiScreenVisible = mutableStateOf(true)
    )
}