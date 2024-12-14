package com.acadify.presentation.simulasinilaiipk

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import com.acadify.presentation.ui.theme.BlueLight2
import java.util.UUID

@Composable
fun TambahSimulasiNilaiScreen(navController: NavController, simulasiViewModel: SimulasiNilaiIPKViewModel) {
    val context = LocalContext.current
    
    val nama = remember { mutableStateOf("") }
    val nilai = remember { mutableStateOf("") }
    val jumlahSKS = remember { mutableStateOf("") }
    
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
                    Toast.makeText(context, "Mohon isi semua field yang ada", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (nama.value.any { it.isDigit() } || nilai.value.toFloatOrNull() == null || jumlahSKS.value.toFloatOrNull() == null) {
                    Toast.makeText(context, "Mohon isi semua field dengan benar", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (nilai.value.toFloat() !in 0.0..4.0) {
                    Toast.makeText(context, "Nilai dalam skala 4", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                simulasiViewModel.addMataKuliah(
                    MataKuliah(
                        id = UUID.randomUUID().toString(), TambahNilai(
                            nama = nama.value,
                            nilai = nilai.value.toFloat(),
                            jumlahSKS = jumlahSKS.value.toFloat()
                        ), null
                    )
                )
                Toast.makeText(context, "Nilai berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                navController.navigate("simulasi_nilai_ipk")
            }) {
                Text("Tambah")
            }
        }
    }
}