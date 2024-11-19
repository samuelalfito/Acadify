package com.acadify.presentation.kelolanilai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai

@Composable
fun DeleteNilaiScreen(
    viewModel: KelolaNilaiViewModel,
    mataKuliah: MataKuliah,
    isDeleteNilaiScreenVisible: MutableState<Boolean>,
) {
    AlertDialog(
        onDismissRequest = { isDeleteNilaiScreenVisible.value = false },
        title = { Text("Delete Nilai") },
        text = { DeleteNilaiScreen(viewModel, mataKuliah, isDeleteNilaiScreenVisible) },
        confirmButton = {
            Button(onClick = {
                // Handle delete action
                isDeleteNilaiScreenVisible.value = false
            }) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = { isDeleteNilaiScreenVisible.value = false }) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun PreviewDeleteNilaiScreen() {
    DeleteNilaiScreen(
        KelolaNilaiViewModel(), MataKuliah(TambahNilai("a", 1.0f, 1.0f)), mutableStateOf(true)
    )
}