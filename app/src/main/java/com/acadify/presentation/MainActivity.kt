package com.acadify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.acadify.presentation.analisisakademik.AnalisisAkademikScreen
import com.acadify.presentation.auth.login.LoginScreen
import com.acadify.presentation.auth.register.RegisterScreen
import com.acadify.presentation.kelolanilai.EditNilaiScreen
import com.acadify.presentation.kelolanilai.KelolaNilaiScreen
import com.acadify.presentation.kelolanilai.TambahNilaiScreen
import com.acadify.presentation.navbar.NavBarViewModel
import com.acadify.presentation.prediksiip.PrediksiIPScreen
import com.acadify.presentation.simulasinilaiipk.EditSimulasiNilaiScreen
import com.acadify.presentation.simulasinilaiipk.SimulasiNilaiIPKScreen
import com.acadify.presentation.simulasinilaiipk.SimulasiNilaiIPKViewModel
import com.acadify.presentation.simulasinilaiipk.TambahSimulasiNilaiScreen
import com.acadify.presentation.ui.theme.AcadifyTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    private val navBarViewModel: NavBarViewModel by viewModels()
    private val simulasiViewModel: SimulasiNilaiIPKViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            AcadifyTheme {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "splash_screen") {
                    composable("splash_screen") {
                        SplashScreen(navController)
                    }
                    
                    composable("login_screen") {
                        LoginScreen(navController)
                    }
                    
                    composable("register_screen") {
                        RegisterScreen(navController)
                    }
                    
                    composable("kelola_nilai") {
                        KelolaNilaiScreen(navController, navBarViewModel)
                    }
                    
                    composable("kelola_tambah_nilai") {
                        TambahNilaiScreen(navController)
                    }
                    
                    composable(
                        "kelola_edit_nilai/{mataKuliahId}",
                        arguments = listOf(navArgument("mataKuliahId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val mataKuliahId = backStackEntry.arguments?.getString("mataKuliahId")
                        if (mataKuliahId != null) {
                            EditNilaiScreen(navController, mataKuliahId)
                        }
                    }
                    
                    composable("prediksi_ip") {
                        PrediksiIPScreen(navController, navBarViewModel)
                    }
                    
                    composable("simulasi_nilai_ipk") {
                        SimulasiNilaiIPKScreen(navController, navBarViewModel, simulasiViewModel)
                    }
                    
                    composable("simulasi_tambah_nilai_ipk"){
                        TambahSimulasiNilaiScreen(navController, simulasiViewModel)
                    }
                    
                    composable(
                        "simulasi_edit_nilai_ipk/{mataKuliahId}",
                        arguments = listOf(navArgument("mataKuliahId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val mataKuliahId = backStackEntry.arguments?.getString("mataKuliahId")
                        if (mataKuliahId != null) {
                            EditSimulasiNilaiScreen(navController, mataKuliahId, simulasiViewModel)
                        }
                    }
                    
                    composable("analisis_akademik") {
                        AnalisisAkademikScreen(navController, navBarViewModel)
                    }
                }
            }
        }
    }
}