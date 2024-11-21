package com.acadify.presentation.analisisakademik

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.acadify.presentation.kelolanilai.KelolaNilaiCard
import com.acadify.presentation.ui.theme.BlueLight2
import com.acadify.presentation.ui.theme.Purple40
import com.acadify.utils.Resource

@Composable
fun AnalisisAkademikScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = viewModel<AnalisisAkademikViewModel>()
    val mataKuliahList = viewModel.mataKuliahList.collectAsState(initial = Resource.Loading())
    
    LaunchedEffect(Unit) {
        viewModel.fetchKelolaNilai()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight2)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("kelola_nilai")
                        }
                ) {
                    Text(
                        "Kelola Nilai", modifier = Modifier
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
                        }
                ) {
                    Text(
                        "Prediksi IP", modifier = Modifier
                            .padding(10.dp)
                            .height(20.dp),
                        color = Color.White
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .border(2.dp, Color.White, shape = RoundedCornerShape(10.dp)),
                    colors = CardDefaults.cardColors(Purple40),
                ) {
                    Text(
                        "Analisis Akademik", modifier = Modifier
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
                        "Simulasi Nilai IPK", modifier = Modifier
                            .padding(10.dp)
                            .height(20.dp),
                        color = Color.White
                    )
                }
            }
        }
        when (val value = mataKuliahList.value) {
            is Resource.Error -> {
                Toast.makeText(
                    context,
                    "Terjadi kesalahan. Coba lagi nanti.",
                    Toast.LENGTH_LONG
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
    AnalisisAkademikScreen(navController = rememberNavController())
}