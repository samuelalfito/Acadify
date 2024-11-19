package com.acadify.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.acadify.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(1500L)
        visible = false
        delay(350L) // Wait for the animation to complete
        navController.navigate("login_screen")
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.student_logo_vector),
                    contentDescription = "Acadify Logo"
                )
                Text(
                    text = "Acadify", fontSize = 36.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}