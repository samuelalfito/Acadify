package com.acadify.presentation.auth.logout

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.acadify.presentation.auth.AuthViewModel

@Composable
fun LogoutScreen(
    isLogoutScreenVisible: MutableState<Boolean>,
    viewModel: AuthViewModel,
) {
    AlertDialog(onDismissRequest = { isLogoutScreenVisible.value = false }, text = {
        Text("Apakah anda ingin keluar dari akun?")
    }, confirmButton = {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary), onClick = {
            viewModel.logout()
            isLogoutScreenVisible.value = false
        }) {
            Text("Iya", color = MaterialTheme.colorScheme.primary)
        }
    }, dismissButton = {
        Button(onClick = { isLogoutScreenVisible.value = false }) {
            Text("Tidak")
        }
    })
}