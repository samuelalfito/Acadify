package com.acadify.presentation.kelolanilai

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.presentation.ui.theme.Orange40
import com.acadify.presentation.ui.theme.Purple40
import com.acadify.presentation.ui.theme.PurpleBlue40
import com.acadify.utils.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun KelolaNilaiScreen(navController: NavController) {
    var isTambahNilaiScreenVisible = remember { mutableStateOf(false) }
    var isEditNilaiScreenVisible = remember { mutableStateOf(false) }
    var isDeleteNilaiScreenVisible = remember { mutableStateOf(false) }
    val selectedMataKuliah = remember { mutableStateOf<MataKuliah?>(null) }
    val viewModel: KelolaNilaiViewModel = viewModel()
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    val context = LocalContext.current
    var index = 1
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai()
    }
    
    SwipeRefresh(modifier = Modifier.fillMaxSize(), state = swipeRefreshState, onRefresh = {
        swipeRefreshState.isRefreshing = true
        viewModel.fetchKelolaNilai()
        swipeRefreshState.isRefreshing = false
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueLight2)
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(2.dp, Color.White, shape = RoundedCornerShape(10.dp)),
                        colors = CardDefaults.cardColors(Purple40),
                    ) {
                        Text(
                            "Kelola Nilai",
                            modifier = Modifier
                                .padding(10.dp)
                                .height(20.dp),
                            color = Color.White
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.navigate("prediksi_ip")
                            },
                    ) {
                        Text(
                            "Prediksi IP",
                            modifier = Modifier
                                .padding(10.dp)
                                .height(20.dp),
                            color = Color.White
                        )
                    }
                    Card(modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("analisis_akademik")
                        }) {
                        Text(
                            "Analisis Akademik",
                            modifier = Modifier
                                .padding(10.dp)
                                .height(20.dp),
                            color = Color.White
                        )
                    }
                    Card(modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("simulasi_nilai_ipk")
                        }) {
                        Text(
                            "Simulasi Nilai IPK",
                            modifier = Modifier
                                .padding(10.dp)
                                .height(20.dp),
                            color = Color.White
                        )
                    }
                }
            }
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
            when (val value = mataKuliahList.value) {
                is Resource.Error -> {
                    Toast.makeText(
                        context, "Terjadi kesalahan. Coba lagi nanti.", Toast.LENGTH_LONG
                    ).show()
                    Log.d("KelolaNilaiScreen", "Error: ${value.msg}")
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
                        modifier = Modifier.weight(1f)
                    ) {
                        if (value.data?.isNotEmpty() == true) {
                            items(value.data) { mataKuliah ->
                                KelolaNilaiCard(mataKuliah = mataKuliah, onEditClick = {
                                    selectedMataKuliah.value = mataKuliah
                                    isEditNilaiScreenVisible.value = true
                                    index = value.data.indexOf(mataKuliah) + 1
                                }, onDeleteClick = {
                                    selectedMataKuliah.value = mataKuliah
                                    isDeleteNilaiScreenVisible.value = true
                                    index = value.data.indexOf(mataKuliah) + 1
                                })
                            }
                        }
                    }
                }
            }
        }
        if (isTambahNilaiScreenVisible.value) {
            TambahNilaiScreen(viewModel, isTambahNilaiScreenVisible)
        }
        if (isEditNilaiScreenVisible.value && selectedMataKuliah.value != null) {
            EditNilaiScreen(
                viewModel,
                selectedMataKuliah.value!!,
                index = index.toString(),
                isEditNilaiScreenVisible
            )
        }
        if (isDeleteNilaiScreenVisible.value && selectedMataKuliah.value != null) {
            DeleteNilaiScreen(viewModel, selectedMataKuliah.value!!, isDeleteNilaiScreenVisible)
        }
    }
}

@Preview
@Composable
fun KelolaNilaiScreenPreview() {
    KelolaNilaiScreen(navController = rememberNavController())
}