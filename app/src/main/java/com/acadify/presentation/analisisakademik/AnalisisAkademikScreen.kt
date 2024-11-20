package com.acadify.presentation.analisisakademik

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AnalisisAkademikScreen(navController: NavController) {
    val viewModel = viewModel<AnalisisAkademikViewModel>()
    
    LaunchedEffect(Unit) {
        viewModel.analisisAkademik()
    }
    
    var isTambahNilaiScreenVisible = remember { mutableStateOf(false) }
    val mataKuliahList = viewModel.mataKuliahList.collectAsState()
    
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
                isTambahNilaiScreenVisible.value = !isTambahNilaiScreenVisible.value
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            if (mataKuliahList.value.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Text("Mata Kuliah masih Kosong")
                    }
                }
            } else {
                items(mataKuliahList.value) { mataKuliah ->
                    AnalisisAcademikCard(mataKuliah = mataKuliah)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnalisisAkademikScreen() {
    AnalisisAkademikScreen(navController = rememberNavController())
}