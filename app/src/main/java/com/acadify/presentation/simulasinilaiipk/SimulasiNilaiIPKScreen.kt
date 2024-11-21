package com.acadify.presentation.simulasinilaiipk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acadify.model.data.MataKuliah
import com.acadify.utils.Resource

@Composable
fun SimulasiNilaiIPKScreen(navController: NavController) {
    var isTambahNilaiScreenVisible = remember { mutableStateOf(false) }
    var isEditNilaiScreenVisible = remember { mutableStateOf(false) }
    var isDeleteNilaiScreenVisible = remember { mutableStateOf(false) }
    val selectedMataKuliah = remember { mutableStateOf<MataKuliah?>(null) }
    val viewModel: SimulasiNilaiIPKViewModel = viewModel()
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(Resource.Loading())
    val simulasiMataKuliah = viewModel.simulasiMataKuliah.collectAsState(emptyList())
    val context = LocalContext.current
    
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai()
    }
    
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
        Text(
            text = "IPK: ${viewModel.hitungIPK(mataKuliahList.value.data)}",
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
        )
        when (val value = mataKuliahList.value) {
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
                    Text("Loading...")
                }
            }
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    if (value.data.isNullOrEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                Text("Mata Kuliah masih Kosong")
                            }
                        }
                    } else {
                        items(value.data) { mataKuliah ->
                            SimulasiNilaiIPKCard(mataKuliah = mataKuliah)
                        }
                    }
                    if (simulasiMataKuliah.value.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                            ) {
                                Text("Mata Kuliah masih Kosong")
                            }
                        }
                    } else {
                        items(simulasiMataKuliah.value) { mataKuliah ->
                            SimulasiNilaiIPKCard(mataKuliah = mataKuliah, onEditClick = {
                                selectedMataKuliah.value = mataKuliah
                                isEditNilaiScreenVisible.value = true
                            }, onDeleteClick = {
                                selectedMataKuliah.value = mataKuliah
                                isDeleteNilaiScreenVisible.value = true
                            })
                        }
                    }
                }
            }
        }
        
    }
    if (isTambahNilaiScreenVisible.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            TambahNilaiScreen(viewModel, isTambahNilaiScreenVisible)
        }
    }
    if (isEditNilaiScreenVisible.value && selectedMataKuliah.value != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            EditNilaiScreen(viewModel, selectedMataKuliah.value!!, isEditNilaiScreenVisible)
        }
    }
    if (isDeleteNilaiScreenVisible.value && selectedMataKuliah.value != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = { isDeleteNilaiScreenVisible.value = false },
                title = { Text("Delete Nilai") },
                text = {
//                    DeleteNilaiScreen(
//                        viewModel,
//                        selectedMataKuliah.value!!,
//                        isDeleteNilaiScreenVisible
//                    )
                },
                confirmButton = {
                    Button(onClick = {
                        // Handle delete action
                        isDeleteNilaiScreenVisible.value = false
                    }) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    Button(onClick = { isDeleteNilaiScreenVisible.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSimulasiNilaiIPK() {
    SimulasiNilaiIPKScreen(navController = rememberNavController())
}