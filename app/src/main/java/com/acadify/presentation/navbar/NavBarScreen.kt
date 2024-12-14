package com.acadify.presentation.navbar

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.R
import com.acadify.presentation.auth.AuthViewModel
import com.acadify.presentation.auth.logout.LogoutScreen
import com.acadify.utils.Resource

@Composable
fun NavBarScreen(navController: NavController, navBarViewModel: NavBarViewModel) {
    val navBarState by navBarViewModel.navBarState
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    
    val loginState = authViewModel.loginState.collectAsState(initial = Resource.Loading<Unit>())
    var isLogoutScreenVisible = remember { mutableStateOf(false) }
    
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            NavBarCard(title = "Kelola Nilai",
                isSelected = navBarState == "kelola_nilai",
                onClick = {
                    navController.navigate("kelola_nilai")
                    navBarViewModel.navBarState.value = "kelola_nilai"
                })
            NavBarCard(title = "Prediksi IP", isSelected = navBarState == "prediksi_ip", onClick = {
                navController.navigate("prediksi_ip")
                navBarViewModel.navBarState.value = "prediksi_ip"
            })
            NavBarCard(title = "Analisis Akademik",
                isSelected = navBarState == "analisis_akademik",
                onClick = {
                    navController.navigate("analisis_akademik")
                    navBarViewModel.navBarState.value = "analisis_akademik"
                })
            NavBarCard(title = "Simulasi Nilai IPK",
                isSelected = navBarState == "simulasi_nilai_ipk",
                onClick = {
                    navController.navigate("simulasi_nilai_ipk")
                    navBarViewModel.navBarState.value = "simulasi_nilai_ipk"
                })
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(onClick = {
                        isLogoutScreenVisible.value = true
                    }),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(20.dp),
                    painter = painterResource(id = R.drawable.ic_logout),
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        }
    }
    if (isLogoutScreenVisible.value) {
        LogoutScreen(
            isLogoutScreenVisible = isLogoutScreenVisible, viewModel = authViewModel
        )
    }
    when (loginState.value) {
        is Resource.Error -> {}
        is Resource.Loading -> {}
        is Resource.Success -> {
            navController.navigate("login_screen") {
                popUpTo(0)
            }
            Toast.makeText(
                context, "Anda keluar dari akun", Toast.LENGTH_SHORT
            ).show()
        }
    }
}