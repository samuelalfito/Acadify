package com.acadify.presentation.analisisakademik

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acadify.presentation.navbar.NavBarScreen
import com.acadify.presentation.navbar.NavBarViewModel
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.utils.Resource

@Composable
fun AnalisisAkademikScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    val context = LocalContext.current
    val viewModel = viewModel<AnalisisAkademikViewModel>()
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai(context)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight2)
    ) {
        NavBarScreen(navController = navController, navBarViewModel = navBarViewModel)
        
        when (val value = mataKuliahList.value) {
            is Resource.Error -> {
                Toast.makeText(
                    context,
                    "Terjadi kesalahan. Coba lagi nanti.",
                    Toast.LENGTH_LONG
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
                            AnalisisAcademikCard(mataKuliah = mataKuliah)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnalisisAkademikScreen() {
    AnalisisAkademikScreen(navController = rememberNavController(), navBarViewModel = viewModel())
}