package com.acadify.presentation.simulasinilaiipk

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun SimulasiNilaiIPKScreen(
    navController: NavController,
    navBarViewModel: NavBarViewModel,
    simulasiViewModel: SimulasiNilaiIPKViewModel,
) {
    var isDeleteNilaiScreenVisible = remember { mutableStateOf(false) }
    var isAturTargetIPKScreenVisible = remember { mutableStateOf(false) }
    val selectedMataKuliah = remember { mutableStateOf<MataKuliah?>(null) }
    val mataKuliahList by simulasiViewModel.mataKuliahList.collectAsState(Resource.Loading())
    val simulasiMataKuliah by simulasiViewModel.simulasiMataKuliah.collectAsState(emptyList())
    val targetIPK by simulasiViewModel.targetIPK.collectAsState()
    
    LaunchedEffect(Unit) {
        simulasiViewModel.fetchKelolaNilai()
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
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
                        text = "Target IPK: $targetIPK",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                    Text(
                        text = "Simulasi: ${simulasiViewModel.hitungIPK(mataKuliahList.data)}",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }
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
            when (val value = mataKuliahList) {
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
                        if (simulasiMataKuliah.isNotEmpty()) {
                            items(simulasiMataKuliah) { mataKuliah ->
                                SimulasiNilaiIPKCard(mataKuliah = mataKuliah, onEditClick = {
                                    navController.navigate("simulasi_edit_nilai_ipk/${mataKuliah.id}")
                                }, onDeleteClick = {
                                    selectedMataKuliah.value = mataKuliah
                                    isDeleteNilaiScreenVisible.value = true
                                })
                            }
                        }
                        if (value.data?.isNotEmpty() == true) {
                            items(value.data) { mataKuliah ->
                                SimulasiNilaiIPKCard(mataKuliah = mataKuliah)
                            }
                        }
                    }
                }
            }
        }
        if (isDeleteNilaiScreenVisible.value && selectedMataKuliah.value != null) {
            DeleteSimulasiNilaiScreen(
                simulasiViewModel, selectedMataKuliah.value!!, isDeleteNilaiScreenVisible
            )
        }
        if (isAturTargetIPKScreenVisible.value) {
            AturTargetIPKScreen(simulasiViewModel, isAturTargetIPKScreenVisible)
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("simulasi_tambah_nilai_ipk")
            },
            containerColor = Orange40,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun PreviewSimulasiNilaiIPK() {
    SimulasiNilaiIPKScreen(navController = rememberNavController(), navBarViewModel = viewModel(), simulasiViewModel = viewModel())
}