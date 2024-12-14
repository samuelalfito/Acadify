package com.acadify.presentation.kelolanilai

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.model.data.KomponenNilai
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.BlueLight2

@Composable
fun TambahNilaiScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: KelolaNilaiViewModel = viewModel()
    
    val nama = remember { mutableStateOf("") }
    val nilai = remember { mutableStateOf("") }
    val jumlahSKS = remember { mutableStateOf("") }
    val tambahKomponen = remember { mutableStateOf(false) }
    val nilaiTugas = remember { mutableStateOf("0") }
    val nilaiKuis = remember { mutableStateOf("0") }
    val nilaiUTS = remember { mutableStateOf("0") }
    val nilaiUAS = remember { mutableStateOf("0") }
    val persentaseTugas = remember { mutableStateOf("25") }
    val persentaseKuis = remember { mutableStateOf("25") }
    val persentaseUTS = remember { mutableStateOf("25") }
    val persentaseUAS = remember { mutableStateOf("25") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight2)
            .padding(16.dp)
    ) {
        OutlinedTextField(value = nama.value,
            onValueChange = { newValue -> nama.value = newValue },
            label = { Text("Nama") },
            modifier = Modifier.padding(bottom = 10.dp),
            maxLines = 1,
            singleLine = true
        )
        OutlinedTextField(value = nilai.value,
            onValueChange = { newValue -> nilai.value = newValue },
            label = { Text("Nilai") },
            modifier = Modifier.padding(bottom = 10.dp),
            maxLines = 1,
            singleLine = true
        )
        OutlinedTextField(value = jumlahSKS.value,
            onValueChange = { newValue -> jumlahSKS.value = newValue },
            label = { Text("Jumlah SKS") },
            modifier = Modifier.padding(bottom = 10.dp),
            maxLines = 1,
            singleLine = true
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = tambahKomponen.value,
                onCheckedChange = { newValue -> tambahKomponen.value = newValue },
            )
            Text("Tambah Komponen", color = MaterialTheme.colorScheme.primary)
        }
        if (tambahKomponen.value) {
            CustomTextField(
                nilaiTugas, "Nilai Tugas", persentaseTugas
            )
            CustomTextField(
                nilaiKuis, "Nilai Kuis", persentaseKuis
            )
            CustomTextField(
                nilaiUTS, "Nilai UTS", persentaseUTS
            )
            CustomTextField(
                nilaiUAS, "Nilai UAS", persentaseUAS
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                onClick = { navController.popBackStack() }) {
                Text("Batal", color = MaterialTheme.colorScheme.primary)
            }
            Button(onClick = {
                if (nama.value.isEmpty() || nilai.value.isEmpty() || jumlahSKS.value.isEmpty()) {
                    Toast.makeText(context, "Mohon isi semua field yang ada", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                if (nama.value.any { it.isDigit() } || nilai.value.toFloatOrNull() == null || jumlahSKS.value.toFloatOrNull() == null) {
                    Toast.makeText(
                        context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                if (nilai.value.toFloat() !in 0.0..4.0) {
                    Toast.makeText(context, "Nilai dalam skala 4", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (tambahKomponen.value) {
                    if (nilaiTugas.value.toFloatOrNull() == null || nilaiKuis.value.toFloatOrNull() == null || nilaiUTS.value.toFloatOrNull() == null || nilaiUAS.value.toFloatOrNull() == null) {
                        Toast.makeText(
                            context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    
                    val percentages = listOf(
                        persentaseTugas to persentaseTugas.value.toFloatOrNull(),
                        persentaseKuis to persentaseKuis.value.toFloatOrNull(),
                        persentaseUTS to persentaseUTS.value.toFloatOrNull(),
                        persentaseUAS to persentaseUAS.value.toFloatOrNull()
                    )
                    
                    val filledPercentages =
                        percentages.filter { it.second != null }.map { it.second!! }
                    val totalFilledPercentage = filledPercentages.sum()
                    
                    if (totalFilledPercentage > 100f) {
                        return@Button
                    }
                    
                    val remainingPercentage = 100f - totalFilledPercentage
                    val emptyFields = percentages.filter { it.second == null }
                    
                    if (emptyFields.isNotEmpty()) {
                        val distributedPercentage = remainingPercentage / emptyFields.size
                        emptyFields.forEach { it.first.value = distributedPercentage.toString() }
                    }
                }
                viewModel.addMataKuliah(
                    MataKuliah(
                        id = "", TambahNilai(
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
                navController.popBackStack()
                Toast.makeText(context, "Nilai berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }) {
                Text("Tambah")
            }
        }
    }
}

@Composable
private fun CustomTextField(
    value: MutableState<String>,
    labelValue: String,
    percentageValue: MutableState<String>,
) {
    Row {
        OutlinedTextField(value = value.value,
            onValueChange = { newValue ->
                handleValueChange(newValue, value)
            },
            label = { Text(labelValue) },
            modifier = Modifier
                .padding(bottom = 10.dp)
                .weight(1f),
            maxLines = 1,
            singleLine = true
        )
        OutlinedTextField(value = percentageValue.value,
            onValueChange = { newValue ->
                handleValueChange(newValue, percentageValue)
            },
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
                .width(90.dp),
            maxLines = 1,
            singleLine = true,
            trailingIcon = { Text("%") })
    }
}

private fun handleValueChange(newValue: String, state: MutableState<String>) {
    val floatValue = newValue.toFloatOrNull()
    if (floatValue != null && floatValue in 0f..100f) {
        state.value = floatValue.toString()
    } else if (newValue.isEmpty()) {
        state.value = ""
    }
}