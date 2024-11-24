package com.acadify.presentation.navbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun NavBarScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    val navBarState by navBarViewModel.navBarState
    
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            NavBarCard(
                title = "Kelola Nilai",
                isSelected = navBarState == "kelola_nilai",
                onClick = {
                    navController.navigate("kelola_nilai")
                    navBarViewModel.navBarState.value = "kelola_nilai"
                }
            )
            NavBarCard(
                title = "Prediksi IP",
                isSelected = navBarState == "prediksi_ip",
                onClick = {
                    navController.navigate("prediksi_ip")
                    navBarViewModel.navBarState.value = "prediksi_ip"
                }
            )
            NavBarCard(
                title = "Analisis Akademik",
                isSelected = navBarState == "analisis_akademik",
                onClick = {
                    navController.navigate("analisis_akademik")
                    navBarViewModel.navBarState.value = "analisis_akademik"
                }
            )
            NavBarCard(
                title = "Simulasi Nilai IPK",
                isSelected = navBarState == "simulasi_nilai_ipk",
                onClick = {
                    navController.navigate("simulasi_nilai_ipk")
                    navBarViewModel.navBarState.value = "simulasi_nilai_ipk"
                }
            )
        }
    }
}