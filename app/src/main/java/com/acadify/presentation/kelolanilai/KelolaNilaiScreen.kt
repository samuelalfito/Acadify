package com.acadify.presentation.kelolanilai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.acadify.model.data.MataKuliah
import androidx.compose.foundation.lazy.items

@Composable
fun KelolaNilaiScreen(navController: NavController) {
    var isTambahNilaiScreenVisible = remember { mutableStateOf(false) }
    val mataKuliahList = remember {
        mutableStateOf(
            listOf(
                MataKuliah("Matematika", 90f, 3f),
                MataKuliah("Fisika", 85f, 3f),
                MataKuliah("Kimia", 88f, 3f),
                MataKuliah("Biologi", 92f, 3f),
                MataKuliah("Sejarah", 80f, 3f),
                MataKuliah("Geografi", 87f, 3f),
                MataKuliah("Ekonomi", 89f, 3f),
                MataKuliah("Sosiologi", 91f, 3f),
                MataKuliah("Bahasa Inggris", 93f, 3f),
                MataKuliah("Bahasa Indonesia", 86f, 3f)
            )
        )
    }
    
    fun updateMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahList.value = mataKuliahList.value.map {
            if (it.nama == mataKuliah.nama) mataKuliah else it
        }
    }
    
    
    fun deleteMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahList.value = mataKuliahList.value.filter { it.nama != mataKuliah.nama }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Row {
                    Card(modifier = Modifier.clickable() {
                        isTambahNilaiScreenVisible.value = !isTambahNilaiScreenVisible.value
                    }) {
                        Text("+")
                    }
                    Card(modifier = Modifier.clickable() {
                        navController.navigate("prediksi-ip")
                    }) {
                        Text("Prediksi IP")
                    }
                }
            }
            items(mataKuliahList.value) { mataKuliah ->
                KelolaNilai(
                    mataKuliah = mataKuliah,
                    onEditClick = { updateMataKuliah(it) },
                    onDeleteClick = { deleteMataKuliah(it) })
            }
        }
    }
    if (isTambahNilaiScreenVisible.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                isTambahNilaiScreenVisible.value = !isTambahNilaiScreenVisible.value
            }) {
                Text("X")
            }
            TambahNilaiScreen()
        }
    }
}

@Preview
@Composable
fun KelolaNilaiScreenPreview() {
    KelolaNilaiScreen(navController = NavController(LocalContext.current))
}