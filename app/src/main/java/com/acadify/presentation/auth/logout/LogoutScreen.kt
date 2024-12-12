package com.acadify.presentation.auth.logout

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.acadify.presentation.auth.AuthViewModel

@Composable
fun LogoutScreen(
    isLogoutScreenVisible: MutableState<Boolean>,
    viewModel: AuthViewModel
) {
    AlertDialog(onDismissRequest = { isLogoutScreenVisible.value = false },
        title = { Text("Logout") },
        text = { Text("Apakah anda ingin keluar dari akun?") },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    viewModel.logout()
                    isLogoutScreenVisible.value = false
                }) {
                Text("Iya", color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = { isLogoutScreenVisible.value = false }) {
                Text("Tidak")
            }
        })
}