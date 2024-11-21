package com.acadify.presentation.simulasinilaiipk

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai

@Composable
fun DeleteSimulasiNilaiScreen(
    viewModel: SimulasiNilaiIPKViewModel,
    mataKuliah: MataKuliah,
    isDeleteNilaiScreenVisible: MutableState<Boolean>,
) {
    val context = LocalContext.current
    
    AlertDialog(onDismissRequest = { isDeleteNilaiScreenVisible.value = false },
        title = { Text("Delete Nilai") },
        text = { Text("Apakah anda yakin menghapus nilai?") },
        confirmButton = {
            Button(onClick = {
                viewModel.deleteMataKuliah(mataKuliah)
                isDeleteNilaiScreenVisible.value = false
                Toast.makeText(
                    context,
                    "Nilai dengan ${mataKuliah.tambahNilai.nama} berhasi dihapus dari daftar",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Text("Hapus")
            }
        },
        dismissButton = {
            Button(onClick = { isDeleteNilaiScreenVisible.value = false }) {
                Text("Tidak")
            }
        })
}

@Preview
@Composable
fun PreviewDeleteSimulasiNilaiScreen() {
    DeleteSimulasiNilaiScreen(
        SimulasiNilaiIPKViewModel(), MataKuliah(TambahNilai("a", 1.0f, 1.0f)), mutableStateOf(true)
    )
}