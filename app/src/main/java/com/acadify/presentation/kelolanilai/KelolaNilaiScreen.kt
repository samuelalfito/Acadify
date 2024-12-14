package com.acadify.presentation.kelolanilai

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.model.data.MataKuliah
import com.acadify.presentation.navbar.NavBarScreen
import com.acadify.presentation.navbar.NavBarViewModel
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.presentation.ui.theme.Orange40
import com.acadify.utils.Resource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun KelolaNilaiScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    val viewModel: KelolaNilaiViewModel = viewModel()
    val context = LocalContext.current
    
    val mataKuliahList by viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    val isDeleteNilaiScreenVisible = remember { mutableStateOf(false) }
    val selectedMataKuliah = remember { mutableStateOf<MataKuliah?>(null) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai()
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueLight2)
        ) {
            NavBarScreen(navController = navController, navBarViewModel = navBarViewModel)
            
            SwipeRefresh(modifier = Modifier.fillMaxSize(), state = swipeRefreshState, onRefresh = {
                swipeRefreshState.isRefreshing = true
                viewModel.fetchKelolaNilai()
                swipeRefreshState.isRefreshing = false
            }) {
                when (val value = mataKuliahList) {
                    is Resource.Error -> {
                        Toast.makeText(
                            context, "Terjadi kesalahan. Coba lagi nanti.", Toast.LENGTH_LONG
                        ).show()
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
                                        navController.navigate("kelola_edit_nilai/${mataKuliah.id}")
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
        }
        if (isDeleteNilaiScreenVisible.value && selectedMataKuliah.value != null) {
            DeleteNilaiScreen(viewModel, selectedMataKuliah.value!!, isDeleteNilaiScreenVisible)
        }
        FloatingActionButton(
            onClick = {
                navController.navigate("kelola_tambah_nilai")
            },
            containerColor = Orange40,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ""
            )
        }
    }
}