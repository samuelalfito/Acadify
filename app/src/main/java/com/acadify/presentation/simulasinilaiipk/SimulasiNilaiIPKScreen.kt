package com.acadify.presentation.simulasinilaiipk

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acadify.model.data.MataKuliah
import com.acadify.presentation.navbar.NavBarScreen
import com.acadify.presentation.navbar.NavBarViewModel
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.presentation.ui.theme.Grey40
import com.acadify.presentation.ui.theme.Orange40
import com.acadify.utils.Resource

@Composable
fun SimulasiNilaiIPKScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    var isTambahNilaiScreenVisible = remember { mutableStateOf(false) }
    var isEditNilaiScreenVisible = remember { mutableStateOf(false) }
    var isDeleteNilaiScreenVisible = remember { mutableStateOf(false) }
    var isAturTargetIPKScreenVisible = remember { mutableStateOf(false) }
    val selectedMataKuliah = remember { mutableStateOf<MataKuliah?>(null) }
    val viewModel: SimulasiNilaiIPKViewModel = viewModel()
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(Resource.Loading())
    val simulasiMataKuliah = viewModel.simulasiMataKuliah.collectAsState(emptyList())
    val targetIPK = viewModel.targetIPK.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight2)
    ) {
        NavBarScreen(navController = navController, navBarViewModel = navBarViewModel)
        
        Card(
            colors = CardDefaults.cardColors(Grey40),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Target IPK: ${targetIPK.value}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
                Text(
                    text = "Simulasi: ${viewModel.hitungIPK(mataKuliahList.value.data)}",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        }
        
        Row {
            Card(
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, bottom = 10.dp)
                    .clickable {
                        isTambahNilaiScreenVisible.value = !isTambahNilaiScreenVisible.value
                    }, colors = CardDefaults.cardColors(Orange40)
            ) {
                Text(
                    "Tambah Nilai", modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center
                )
            }
            Card(
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, bottom = 10.dp)
                    .clickable {
                        isAturTargetIPKScreenVisible.value = !isAturTargetIPKScreenVisible.value
                    }, colors = CardDefaults.cardColors(Orange40)
            ) {
                Text(
                    "Atur Target IPK",
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        when (val value = mataKuliahList.value) {
            is Resource.Error -> {
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
                    if (value.data?.isNotEmpty() == true) {
                        items(value.data) { mataKuliah ->
                            SimulasiNilaiIPKCard(mataKuliah = mataKuliah)
                        }
                    }
                    if (simulasiMataKuliah.value.isNotEmpty()) {
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
        TambahSimulasiNilaiScreen(viewModel, isTambahNilaiScreenVisible)
    }
    if (isEditNilaiScreenVisible.value && selectedMataKuliah.value != null) {
        EditSimulasiNilaiScreen(viewModel, selectedMataKuliah.value!!, isEditNilaiScreenVisible)
    }
    if (isDeleteNilaiScreenVisible.value && selectedMataKuliah.value != null) {
        DeleteSimulasiNilaiScreen(
            viewModel, selectedMataKuliah.value!!, isDeleteNilaiScreenVisible
        )
    }
    if (isAturTargetIPKScreenVisible.value) {
        AturTargetIPKScreen(viewModel, isAturTargetIPKScreenVisible)
    }
}

@Preview
@Composable
fun PreviewSimulasiNilaiIPK() {
    SimulasiNilaiIPKScreen(navController = rememberNavController(), navBarViewModel = viewModel())
}