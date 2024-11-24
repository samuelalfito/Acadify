package com.acadify.presentation.kelolanilai

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import kotlin.toString

@Composable
fun EditNilaiScreen(
    viewModel: KelolaNilaiViewModel,
    mataKuliah: MataKuliah,
    isEditNilaiScreenVisible: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val showWarningDialog = remember { mutableStateOf(false) }
    
    val nama = remember { mutableStateOf(mataKuliah.tambahNilai.nama) }
    val nilai = remember { mutableStateOf(mataKuliah.tambahNilai.nilai.toString()) }
    val jumlahSKS = remember { mutableStateOf(mataKuliah.tambahNilai.jumlahSKS.toString()) }
    val tambahKomponen = remember { mutableStateOf(mataKuliah.komponenNilai != null) }
    val nilaiTugas =
        remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiTugas?.toString() ?: "0") }
    val nilaiKuis =
        remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiKuis?.toString() ?: "0") }
    val nilaiUTS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiUTS?.toString() ?: "0") }
    val nilaiUAS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiUAS?.toString() ?: "0") }
    val persentaseTugas =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseTugas?.toString() ?: "25") }
    val persentaseKuis =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseKuis?.toString() ?: "25") }
    val persentaseUTS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseUTS?.toString() ?: "25") }
    val persentaseUAS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseUAS?.toString() ?: "25") }
    
    if (showWarningDialog.value) {
        AlertDialog(onDismissRequest = { showWarningDialog.value = false },
            title = { Text("Peringatan") },
            text = { Text("Apakah anda yakin untuk meninggalkan halaman?") },
            confirmButton = {
                Button(onClick = {
                    showWarningDialog.value = false
                    isEditNilaiScreenVisible.value = false
                }) {
                    Text("Iya")
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
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .weight(1f),
                        maxLines = 1,
                        singleLine = true
                    )
                    OutlinedTextField(value = persentaseTugas.value,
                        onValueChange = { newValue: String ->
                            val intValue = newValue.toIntOrNull() ?: ""
                            if (intValue in 0..100) {
                                persentaseTugas.value = intValue.toString()
                            } else if (newValue.isEmpty()) {
                                persentaseTugas.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .width(90.dp),
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
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .weight(1f),
                        maxLines = 1,
                        singleLine = true
                    )
                    OutlinedTextField(value = persentaseKuis.value,
                        onValueChange = { newValue: String ->
                            val intValue = newValue.toIntOrNull() ?: ""
                            if (intValue in 0..100) {
                                persentaseKuis.value = intValue.toString()
                            } else if (newValue.isEmpty()) {
                                persentaseTugas.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .width(90.dp),
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
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .weight(1f),
                        maxLines = 1,
                        singleLine = true
                    )
                    OutlinedTextField(value = persentaseUTS.value,
                        onValueChange = { newValue: String ->
                            val intValue = newValue.toIntOrNull() ?: ""
                            if (intValue in 0..100) {
                                persentaseUTS.value = intValue.toString()
                            } else if (newValue.isEmpty()) {
                                persentaseTugas.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .width(90.dp),
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
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .weight(1f),
                        maxLines = 1,
                        singleLine = true
                    )
                    OutlinedTextField(value = persentaseUAS.value,
                        onValueChange = { newValue: String ->
                            val intValue = newValue.toIntOrNull()
                            if (intValue != null && intValue in 0..100) {
                                persentaseUAS.value = intValue.toString()
                            } else if (newValue.isEmpty()) {
                                persentaseUAS.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .width(90.dp),
                        maxLines = 1,
                        singleLine = true,
                        trailingIcon = {
                            Text("%")
                        })
                }
            }
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
                Toast.makeText(
                    context, "Mohon isi semua field yang ada", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (nilai.value.toFloat() !in 0.0..4.0) {
                Toast.makeText(
                    context, "Nilai dalam skala 4", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            if (tambahKomponen.value) {
                if (nilaiTugas.value.isEmpty() || nilaiTugas.value.toFloatOrNull() == null || nilaiKuis.value.isEmpty() || nilaiKuis.value.toFloatOrNull() == null || nilaiUTS.value.isEmpty() || nilaiUTS.value.toFloatOrNull() == null || nilaiUAS.value.isEmpty() || nilaiUAS.value.toFloatOrNull() == null) {
                    Toast.makeText(
                        context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                if (nilaiTugas.value.isEmpty()) nilaiTugas.value = "0"
                if (nilaiKuis.value.isEmpty()) nilaiKuis.value = "0"
                if (nilaiUTS.value.isEmpty()) nilaiUTS.value = "0"
                if (nilaiUAS.value.isEmpty()) nilaiUAS.value = "0"
                
                val percentages = listOf(
                    persentaseTugas to persentaseTugas.value.toFloatOrNull(),
                    persentaseKuis to persentaseKuis.value.toFloatOrNull(),
                    persentaseUTS to persentaseUTS.value.toFloatOrNull(),
                    persentaseUAS to persentaseUAS.value.toFloatOrNull()
                )
                
                val filledPercentages = percentages.filter { it.second != null }.map { it.second!! }
                val totalFilledPercentage = filledPercentages.sum()
                val remainingPercentage = 100f - totalFilledPercentage
                val emptyFields = percentages.filter { it.second == null }
                
                if (emptyFields.isNotEmpty()) {
                    val distributedPercentage = remainingPercentage / emptyFields.size
                    emptyFields.forEach { it.first.value = distributedPercentage.toString() }
                }
            }
            viewModel.updateMataKuliah(
                MataKuliah(
                    id = mataKuliah.id,
                    tambahNilai = TambahNilai(
                        nama = nama.value,
                        nilai = nilai.value.toFloat(),
                        jumlahSKS = jumlahSKS.value.toFloat()
                    ),
                    komponenNilai = if (tambahKomponen.value) {
                        KomponenNilai(
                            nilaiTugas = nilaiTugas.value.toFloat(),
                            nilaiKuis = nilaiKuis.value.toFloat(),
                            nilaiUTS = nilaiUTS.value.toFloat(),
                            nilaiUAS = nilaiUAS.value.toFloat(),
                            persentaseTugas = persentaseTugas.value.toInt(),
                            persentaseKuis = persentaseKuis.value.toInt(),
                            persentaseUTS = persentaseUTS.value.toInt(),
                            persentaseUAS = persentaseUAS.value.toInt()
                        )
                    } else {
                        null
                    }
                )
            )
            isEditNilaiScreenVisible.value = false
            Toast.makeText(context, "Nilai berhasil diubah", Toast.LENGTH_SHORT).show()
        }) {
            Text("Simpan")
        }
    }, dismissButton = {
        Button(onClick = { isEditNilaiScreenVisible.value = false }) {
            Text("Batal")
        }
    })
}

@Preview
@Composable
fun EditNilaiScreenPreview() {
    EditNilaiScreen(
        viewModel = KelolaNilaiViewModel(), mataKuliah = MataKuliah(
            id = "",
            tambahNilai = TambahNilai(
                nama = "Pemrograman Berorientasi Objek", nilai = 4.0f, jumlahSKS = 3.0f
            ), komponenNilai = KomponenNilai(
                nilaiTugas = 100.0f,
                nilaiKuis = 100.0f,
                nilaiUTS = 100.0f,
                nilaiUAS = 100.0f,
                persentaseTugas = 25,
                persentaseKuis = 25,
                persentaseUTS = 25,
                persentaseUAS = 25
            )
        ), isEditNilaiScreenVisible = mutableStateOf(true)
    )
}