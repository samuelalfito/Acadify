package com.acadify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acadify.presentation.analisisakademik.AnalisisAkademikScreen
import com.acadify.presentation.auth.login.LoginScreen
import com.acadify.presentation.auth.register.RegisterScreen
import com.acadify.presentation.kelolanilai.KelolaNilaiScreen
import com.acadify.presentation.prediksiip.PrediksiIPScreen
import com.acadify.presentation.prediksiip.PrediksiIPViewModel
import com.acadify.presentation.simulasinilaiipk.SimulasiNilaiIPKScreen
import com.acadify.presentation.ui.theme.AcadifyTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
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
                        KelolaNilaiScreen(navController)
                    }
                    
                    composable("prediksi_ip") {
                        PrediksiIPScreen(navController)
                    }
                    
                    composable("simulasi_nilai_ipk") {
                        SimulasiNilaiIPKScreen(navController)
                    }
                    
                    composable("analisis_akademik") {
                        AnalisisAkademikScreen(navController)
                    }
                }
            }
        }
    }
}