package com.acadify.presentation.prediksiip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.model.data.PrediksiIP
import com.acadify.utils.Resource

@Composable
fun PrediksiIPScreen(navController: NavController) {
    val viewModel: PrediksiIPViewModel = viewModel()
    
    LaunchedEffect(Unit) {
        viewModel.hitungPrediksiIP()
    }
    
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    val prediksiIPList = viewModel.prediksiIP.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Card(modifier = Modifier.clickable {
                navController.navigate("kelola_nilai")
            }) {
                Text(
                    "+",
                    modifier = Modifier
                        .padding(20.dp)
                        .size(20.dp),
                    textAlign = TextAlign.Center
                )
            }
            Card(modifier = Modifier.clickable {
                navController.navigate("prediksi_ip")
            }) {
                Text(
                    "Prediksi IP", modifier = Modifier
                        .padding(20.dp)
                        .height(20.dp)
                )
            }
            Card(modifier = Modifier.clickable {
                navController.navigate("analisis_akademik")
            }) {
                Text(
                    "Analisis Akademik", modifier = Modifier
                        .padding(20.dp)
                        .height(20.dp)
                )
            }
            Card(modifier = Modifier.clickable {
                navController.navigate("simulasi_nilai_ipk")
            }) {
                Text(
                    "Simulasi Nilai IPK", modifier = Modifier
                        .padding(20.dp)
                        .height(20.dp)
                )
            }
        }
        when (mataKuliahList.value) {
            is Resource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text("Error")
                }
            }
            
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    if (mataKuliahList.value.data?.isEmpty() == true) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                Text("Mata Kuliah masih Kosong")
                            }
                        }
                    } else {
                        items(count = mataKuliahList.value.data?.size ?: 0) { index ->
                            val mataKuliah = mataKuliahList.value.data?.get(index)
                            if (mataKuliah != null) {
                                val prediksiIP = PrediksiIP(
                                    nama = listOf(mataKuliah.tambahNilai.nama),
                                    nilaiAkhir = listOf(prediksiIPList.value.nilaiAkhir.getOrElse(index) { 0f }),
                                    prediksiIP = prediksiIPList.value.prediksiIP
                                )
                                PrediksiIPCard(mataKuliah = mataKuliah, prediksiIP = prediksiIP)
                            }
                        }
                    }
                }
            }
        }
        
    }
}

@Preview
@Composable
fun PrediksiIPScreenPreview() {
    PrediksiIPScreen(navController = NavController(LocalContext.current))
}