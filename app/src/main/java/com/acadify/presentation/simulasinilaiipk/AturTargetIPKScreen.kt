package com.acadify.presentation.simulasinilaiipk

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AturTargetIPKScreen(
    viewModel: SimulasiNilaiIPKViewModel,
    isAturTargetIPKScreenVisible: MutableState<Boolean>,
) {
    val context = LocalContext.current
    var targetIPK = remember { mutableStateOf("") }
    
    AlertDialog(onDismissRequest = { isAturTargetIPKScreenVisible.value = false },
        title = { Text("Target IPK") },
        text = {
            OutlinedTextField(
                value = targetIPK.value,
                onValueChange = { newValue -> targetIPK.value = newValue },
                placeholder = { Text("Target IPK") },
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = {
                val targetIPKValue = targetIPK.value.toFloatOrNull()
                if (targetIPKValue == null) {
                    return@Button
                }
                if (targetIPKValue < 0 || targetIPKValue > 4) {
                    Toast.makeText(context, "Rentang Target IPK adalah 0 - 4", Toast.LENGTH_SHORT)
                        .show()
                    return@Button
                }
                viewModel.updateTargetIPK(targetIPKValue)
                Toast.makeText(context, "Target IPK tersimpan", Toast.LENGTH_SHORT).show()
                isAturTargetIPKScreenVisible.value = false
            }) {
                Text("Simpan")
            }
        })
}