package com.acadify.presentation.analisisakademik

import androidx.lifecycle.ViewModel
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AnalisisAkademikViewModel : ViewModel() {
    
    private val _mataKuliahList = MutableStateFlow(
        listOf(
            MataKuliah(TambahNilai("Matematika", 90f, 3f)),
            MataKuliah(TambahNilai("Fisika", 85f, 3f)),
            MataKuliah(TambahNilai("Kimia", 88f, 3f)),
            MataKuliah(TambahNilai("Biologi", 92f, 3f)),
            MataKuliah(TambahNilai("Bahasa Indonesia", 85f, 2f)),
            MataKuliah(TambahNilai("Bahasa Inggris", 90f, 2f)),
            MataKuliah(TambahNilai("Pendidikan Agama", 35f, 2f)),
            MataKuliah(TambahNilai("Pendidikan Agama", 85f, 2f)),
        )
    )
    
    val mataKuliahList: StateFlow<List<MataKuliah>> = _mataKuliahList
    
    fun analisisAkademik() {
        _mataKuliahList.value = _mataKuliahList.value.sortedByDescending { it.tambahNilai.nilai }
    }
}