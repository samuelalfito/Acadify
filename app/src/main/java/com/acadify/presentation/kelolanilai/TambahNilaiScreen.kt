package com.acadify.presentation.kelolanilai

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.KomponenNilai
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai

@Composable
fun TambahNilaiScreen(
    viewModel: KelolaNilaiViewModel,
    isTambahNilaiScreenVisible: MutableState<Boolean>,
) {
    var nama = remember { mutableStateOf("") }
    var nilai = remember { mutableStateOf("") }
    var jumlahSKS = remember { mutableStateOf("") }
    var tambahKomponen = remember { mutableStateOf(false) }
    var nilaiTugas = remember { mutableStateOf("") }
    var nilaiKuis = remember { mutableStateOf("") }
    var nilaiUTS = remember { mutableStateOf("") }
    var nilaiUAS = remember { mutableStateOf("") }
    var persentaseTugas = remember { mutableStateOf("") }
    var persentaseKuis = remember { mutableStateOf("") }
    var persentaseUTS = remember { mutableStateOf("") }
    var persentaseUAS = remember { mutableStateOf("") }
    val context = LocalContext.current
    val showWarningDialog = remember { mutableStateOf(false) }
    
    if (showWarningDialog.value) {
        AlertDialog(
            onDismissRequest = { showWarningDialog.value = false },
            title = { Text("Peringatan") },
            text = { Text("Apakah anda yakin untuk meninggalkan halaman?") },
            confirmButton = {
                Button(onClick = {
                    showWarningDialog.value = false
                    isTambahNilaiScreenVisible.value = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(onClick = { showWarningDialog.value = false }) {
                    Text("Tidak")
                }
            }
        )
    }
    
    AlertDialog(
        onDismissRequest = {
            showWarningDialog.value = true
        },
        title = { Text("Tambah Nilai") },
        text = {
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = tambahKomponen.value,
                        onCheckedChange = { newValue -> tambahKomponen.value = newValue },
                    )
                    Text("Tambah Komponen")
                }
                if (tambahKomponen.value) {
                    Row {
                        OutlinedTextField(
                            value = nilaiTugas.value,
                            onValueChange = { newValue: String ->
                                nilaiTugas.value = newValue
                            },
                            placeholder = { Text("Nilai Tugas") },
                            modifier = Modifier.padding(bottom = 10.dp).weight(1f),
                            maxLines = 1,
                            singleLine = true
                        )
                        OutlinedTextField(value = persentaseTugas.value,
                            onValueChange = { newValue: String ->
                                val intValue = newValue.toInt()
                                if (intValue in 0..100) {
                                    persentaseTugas.value = intValue.toString()
                                }
                            },
                            modifier = Modifier.padding(bottom = 10.dp).width(90.dp),
                            maxLines = 1,
                            singleLine = true,
                            trailingIcon = {
                                Text("%")
                            })
                    }
                    Row {
                        OutlinedTextField(
                            value = nilaiKuis.value,
                            onValueChange = { newValue: String ->
                                nilaiKuis.value = newValue
                            },
                            placeholder = { Text("Nilai Kuis") },
                            modifier = Modifier.padding(bottom = 10.dp).weight(1f),
                            maxLines = 1,
                            singleLine = true
                        )
                        OutlinedTextField(value = persentaseKuis.value,
                            onValueChange = { newValue: String ->
                                val intValue = newValue.toIntOrNull()
                                if (intValue != null && intValue in 0..100) {
                                    persentaseKuis.value = intValue.toString()
                                }
                            },
                            modifier = Modifier.padding(bottom = 10.dp).width(90.dp),
                            maxLines = 1,
                            singleLine = true,
                            trailingIcon = {
                                Text("%")
                            })
                    }
                    Row {
                        OutlinedTextField(
                            value = nilaiUTS.value,
                            onValueChange = { newValue: String ->
                                nilaiUTS.value = newValue
                            },
                            placeholder = { Text("Nilai UTS") },
                            modifier = Modifier.padding(bottom = 10.dp).weight(1f),
                            maxLines = 1,
                            singleLine = true
                        )
                        OutlinedTextField(value = persentaseUTS.value,
                            onValueChange = { newValue: String ->
                                val intValue = newValue.toIntOrNull()
                                if (intValue != null && intValue in 0..100) {
                                    persentaseUTS.value = intValue.toString()
                                }
                            },
                            modifier = Modifier.padding(bottom = 10.dp).width(90.dp),
                            maxLines = 1,
                            singleLine = true,
                            trailingIcon = {
                                Text("%")
                            })
                    }
                    Row {
                        OutlinedTextField(
                            value = nilaiUAS.value,
                            onValueChange = { newValue: String ->
                                nilaiUAS.value = newValue
                            },
                            placeholder = { Text("Nilai UAS") },
                            modifier = Modifier.padding(bottom = 10.dp).weight(1f),
                            maxLines = 1,
                            singleLine = true
                        )
                        OutlinedTextField(value = persentaseUAS.value,
                            onValueChange = { newValue: String ->
                                val intValue = newValue.toIntOrNull()
                                if (intValue != null && intValue in 0..100) {
                                    persentaseUAS.value = intValue.toString()
                                }
                            },
                            modifier = Modifier.padding(bottom = 10.dp).width(90.dp),
                            maxLines = 1,
                            singleLine = true,
                            trailingIcon = {
                                Text("%")
                            })
                    }
                }
            }
        },
        confirmButton = {
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
                        TambahNilai(
                            nama = nama.value,
                            nilai = nilai.value.toFloat(),
                            jumlahSKS = jumlahSKS.value.toFloat()
                        ),
                        if (tambahKomponen.value) {
                            KomponenNilai(
                                nilaiTugas = nilaiTugas.value.toFloat(),
                                nilaiKuis = nilaiKuis.value.toFloat(),
                                nilaiUTS = nilaiUTS.value.toFloat(),
                                nilaiUAS = nilaiUAS.value.toFloat(),
                                persentaseTugas = persentaseTugas.value.toFloat(),
                                persentaseKuis = persentaseKuis.value.toFloat(),
                                persentaseUTS = persentaseUTS.value.toFloat(),
                                persentaseUAS = persentaseUAS.value.toFloat()
                            )
                        } else {
                            null
                        }
                    )
                )
                isTambahNilaiScreenVisible.value = false
                Toast.makeText(context, "Nilai berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tambah")
            }
        },
        dismissButton = {
            Button(onClick = { isTambahNilaiScreenVisible.value = false }) {
                Text("Batal")
            }
        }
    )
}

@Preview
@Composable
fun TambahNilaiScreenPreview() {
    TambahNilaiScreen(KelolaNilaiViewModel(), mutableStateOf(true))
//    TambahNilaiScreen(mutableStateOf(true))
}
