package com.acadify.presentation.prediksiip

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.model.data.PrediksiIP
import com.acadify.presentation.navbar.NavBarScreen
import com.acadify.presentation.navbar.NavBarViewModel
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.presentation.ui.theme.Grey40
import com.acadify.utils.Resource
import java.util.Locale

@Composable
fun PrediksiIPScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    val viewModel: PrediksiIPViewModel = viewModel()
    val context = LocalContext.current
    val mataKuliahList by viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    val prediksiIPList by viewModel.prediksiIP.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.hitungPrediksiIP(context)
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
                    String.format(
                        Locale.getDefault(), "Prediksi IP\n%.2f", prediksiIPList.prediksiIP
                    ), color = Color.White, textAlign = TextAlign.Center
                )
            }
        }
        
        when (mataKuliahList) {
            is Resource.Error -> {
                Toast.makeText(context, "Terjadi kesalahan. Coba lagi nanti.", Toast.LENGTH_SHORT).show()
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
                    if (mataKuliahList.data?.isNotEmpty() == true) {
                        items(count = mataKuliahList.data?.size ?: 0) { index ->
                            val mataKuliah = mataKuliahList.data?.get(index)
                            if (mataKuliah != null) {
                                val prediksiIP = PrediksiIP(
                                    nama = listOf(mataKuliah.tambahNilai.nama),
                                    nilaiAkhir = listOf(prediksiIPList.nilaiAkhir.getOrElse(
                                        index
                                    ) { 0f }),
                                    prediksiIP = prediksiIPList.prediksiIP
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
    PrediksiIPScreen(
        navController = NavController(LocalContext.current), navBarViewModel = NavBarViewModel()
    )
}