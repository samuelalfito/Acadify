package com.acadify.presentation.auth.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.acadify.R
import com.acadify.model.repository.network.FireAuth
import com.acadify.presentation.auth.AuthViewModel
import com.acadify.presentation.ui.theme.Green40
import com.acadify.presentation.ui.theme.NunitoFontFamily
import com.acadify.presentation.ui.theme.PurpleBlue40
import com.acadify.presentation.ui.theme.PurpleBlue80
import com.acadify.utils.Resource
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var confirmPassword = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    var confirmPasswordVisible = remember { mutableStateOf(false) }
    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val registerState = viewModel.registerState.collectAsState(initial = Resource.Loading<Unit>())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(PurpleBlue40, PurpleBlue80)
                )
            )
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Register",
            color = Color.White,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(120.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Email Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp, end = 6.dp)
                        .size(20.dp)
                )
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp),
                    thickness = 2.dp,
                    color = Green40
                )
                TextField(value = email.value,
                    onValueChange = { newValue -> email.value = newValue },
                    placeholder = { Text("Email") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Email Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .padding(horizontal = 10.dp)
                )
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp),
                    thickness = 2.dp,
                    color = Green40
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = password.value,
                    onValueChange = { newValue -> password.value = newValue },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                Icon(painter = painterResource(id = if (passwordVisible.value) R.drawable.eye_on else R.drawable.eye_off),
                    contentDescription = if (passwordVisible.value) "Hide Password" else "Show Password",
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .clickable {
                            passwordVisible.value = !passwordVisible.value
                        })
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Email Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .padding(horizontal = 10.dp)
                )
                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp),
                    thickness = 2.dp,
                    color = Green40
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = confirmPassword.value,
                    onValueChange = { newValue -> confirmPassword.value = newValue },
                    placeholder = { Text("Konfirmasi Password") },
                    visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                Icon(painter = painterResource(id = if (confirmPasswordVisible.value) R.drawable.eye_on else R.drawable.eye_off),
                    contentDescription = if (confirmPasswordVisible.value) "Hide Password" else "Show Password",
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .clickable {
                            confirmPasswordVisible.value = !confirmPasswordVisible.value
                        })
            }
        }
        Spacer(modifier = Modifier.padding(60.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                elevation = ButtonDefaults.buttonElevation(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green40),
                onClick = {
                    if (email.value.isEmpty() && password.value.isEmpty()) {
                        Toast.makeText(
                            context, "Mohon isi semua field yang ada", Toast.LENGTH_SHORT
                        ).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(
                            context,
                            "Register gagal, Password dengan konfirmasi password tidak sama",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.register(email.value, password.value)
                        navController.navigate("login_screen") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                }) {
                Text(
                    text = "Register",
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sudah punya akun? Masuk",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("login_screen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                })
        }
        when (registerState.value) {
            is Resource.Error -> {
                if (registerState.value.msg != null) {
                    if (registerState.value.msg!!.contains(
                            "wrong password", ignoreCase = true
                        ) || registerState.value.msg!!.contains("no user record", ignoreCase = true)
                    ) {
                        Toast.makeText(
                            context,
                            "Login gagal, Email atau password tidak sesuai",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
//                        Toast.makeText(context, loginState.value.msg, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        context, "Terjadi kesalahan. Coba lagi nanti.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            
            is Resource.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            
            is Resource.Success -> {
                navController.navigate("login_screen") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = NavController(LocalContext.current))
}