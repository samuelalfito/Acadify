package com.acadify.presentation.kelolanilai

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.R
import com.acadify.model.data.KomponenNilai
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai

@Composable
fun EditNilaiScreen(
    viewModel: KelolaNilaiViewModel,
    mataKuliah: MataKuliah,
    index: String,
    isEditNilaiScreenVisible: MutableState<Boolean>,
) {
    var nama = remember { mutableStateOf(mataKuliah.tambahNilai.nama) }
    var nilai = remember { mutableStateOf(mataKuliah.tambahNilai.nilai.toString()) }
    var jumlahSKS = remember { mutableStateOf(mataKuliah.tambahNilai.jumlahSKS.toString()) }
    var tambahKomponen = remember { mutableStateOf(mataKuliah.komponenNilai != null) }
    var nilaiTugas = remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiTugas.toString()) }
    var nilaiKuis = remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiKuis.toString()) }
    var nilaiUTS = remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiUTS.toString()) }
    var nilaiUAS = remember { mutableStateOf(mataKuliah.komponenNilai?.nilaiUAS.toString()) }
    var persentaseTugas =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseTugas.toString()) }
    var persentaseKuis =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseKuis.toString()) }
    var persentaseUTS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseUTS.toString()) }
    var persentaseUAS =
        remember { mutableStateOf(mataKuliah.komponenNilai?.persentaseUAS.toString()) }
    val context = LocalContext.current
    val showWarningDialog = remember { mutableStateOf(false) }
    
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
            OutlinedTextField(value = nama.value,
                onValueChange = { newValue -> nama.value = newValue },
                placeholder = { Text("Nama") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
            OutlinedTextField(value = nilai.value,
                onValueChange = { newValue -> nilai.value = newValue },
                placeholder = { Text("Nilai") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
            OutlinedTextField(value = jumlahSKS.value,
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
                    OutlinedTextField(value = nilaiTugas.value,
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
                            .padding(bottom = 10.dp)
                            .width(90.dp),
                        maxLines = 1,
                        singleLine = true,
                        trailingIcon = {
                            Text("%")
                        })
                }
                Row {
                    OutlinedTextField(value = nilaiKuis.value,
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
                            .padding(bottom = 10.dp)
                            .width(90.dp),
                        maxLines = 1,
                        singleLine = true,
                        trailingIcon = {
                            Text("%")
                        })
                }
                Row {
                    OutlinedTextField(value = nilaiUTS.value,
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
                            .padding(bottom = 10.dp)
                            .width(90.dp),
                        maxLines = 1,
                        singleLine = true,
                        trailingIcon = {
                            Text("%")
                        })
                }
                Row {
                    OutlinedTextField(value = nilaiUAS.value,
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
                            val intValue = newValue.toIntOrNull() ?: ""
                            if (intValue in 0..100) {
                                persentaseUAS.value = intValue.toString()
                            } else if (newValue.isEmpty()) {
                                persentaseTugas.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(bottom = 10.dp)
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
                return@Button
            }
            if (tambahKomponen.value && (nilaiTugas.value.toFloatOrNull() == null || nilaiKuis.value.isEmpty() || nilaiUTS.value.isEmpty() || nilaiUAS.value.isEmpty() || persentaseTugas.value.isEmpty() || persentaseKuis.value.isEmpty() || persentaseUTS.value.isEmpty() || persentaseUAS.value.isEmpty())) {
                Toast.makeText(
                    context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                ).show()
                return@Button
            }
            viewModel.updateMataKuliah(
                documentId = index, MataKuliah(
                    TambahNilai(
                        nama = nama.value,
                        nilai = nilai.value.toFloat(),
                        jumlahSKS = jumlahSKS.value.toFloat()
                    ), if (tambahKomponen.value) {
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
            tambahNilai = TambahNilai(
                nama = "Pemrograman Berorientasi Objek", nilai = 4.0f, jumlahSKS = 3.0f
            ), komponenNilai = KomponenNilai(
                nilaiTugas = 100.0f,
                nilaiKuis = 100.0f,
                nilaiUTS = 100.0f,
                nilaiUAS = 100.0f,
                persentaseTugas = 25.0f,
                persentaseKuis = 25.0f,
                persentaseUTS = 25.0f,
                persentaseUAS = 25.0f
            )
        ), index = "0", isEditNilaiScreenVisible = mutableStateOf(true)
    )
}