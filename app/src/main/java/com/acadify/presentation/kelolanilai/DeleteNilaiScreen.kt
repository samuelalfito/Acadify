package com.acadify.presentation.kelolanilai

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
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
        text = { Text("Apakah anda yakin menghapus nilai?") },
        confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                onClick = {
                    viewModel.deleteMataKuliah(mataKuliah.id)
                    isDeleteNilaiScreenVisible.value = false
                }) {
                Text("Hapus", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            Button(onClick = { isDeleteNilaiScreenVisible.value = false }) {
                Text("Tidak")
            }
        }
    )
}

@Preview
@Composable
fun PreviewDeleteNilaiScreen() {
    DeleteNilaiScreen(
        KelolaNilaiViewModel(), MataKuliah("", TambahNilai("a", 1.0f, 1.0f)), mutableStateOf(true)
    )
}