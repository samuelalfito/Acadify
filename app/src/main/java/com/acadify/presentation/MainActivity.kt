package com.acadify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acadify.ui.theme.AcadifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcadifyTheme {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "") {
                    composable("") {
                    }
                    
                    composable("") {
                    }
                }
            }
        }
    }
}