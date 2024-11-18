package com.acadify.presentation.auth.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acadify.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var passwordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Login"
        )
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "Email Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 10.dp),
                    color = Color.Gray
                )
                TextField(
                    value = email.value,
                    onValueChange = { newValue -> email.value = newValue },
                    placeholder = { Text("Email") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
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
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Email Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 10.dp)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(vertical = 10.dp),
                    color = Color.Gray
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = password.value,
                    onValueChange = { newValue -> password.value = newValue },
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                Icon(
                    painter = painterResource(id = if (passwordVisible.value) R.drawable.eye_on else R.drawable.eye_off),
                    contentDescription = if (passwordVisible.value) "Hide Password" else "Show Password",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .clickable {
                            passwordVisible.value = !passwordVisible.value
                        }
                )
            }
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                onClick = {
                    auth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                navController.navigate("kelola_nilai")
                            } else {
                                Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                }) {
                Text(
                    text = "Login"
                )
            }
        }
        Button(onClick = {navController.navigate("register_screen")}) {
            Text(
                text = "Don't have an account? Register"
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(navController = NavController(LocalContext.current))
}