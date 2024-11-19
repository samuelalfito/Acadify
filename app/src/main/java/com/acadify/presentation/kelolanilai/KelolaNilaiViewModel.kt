package com.acadify.presentation.kelolanilai

import android.util.Log
import androidx.lifecycle.ViewModel
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.TambahNilai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KelolaNilaiViewModel : ViewModel() {
    
    val _mataKuliahList = MutableStateFlow(
        listOf(
            MataKuliah(TambahNilai("Matematika", 90f, 3f)),
            MataKuliah(TambahNilai("Fisika", 85f, 3f)),
            MataKuliah(TambahNilai("Kimia", 88f, 3f)),
            MataKuliah(TambahNilai("Biologi", 92f, 3f)),
            MataKuliah(TambahNilai("Bahasa Indonesia", 85f, 2f)),
            MataKuliah(TambahNilai("Bahasa Inggris", 90f, 2f)),
            MataKuliah(TambahNilai("Pendidikan Agama", 85f, 2f)),
            MataKuliah(TambahNilai("Pendidikan Agama", 85f, 2f)),
        )
    )
    val mataKuliahList: StateFlow<List<MataKuliah>> = _mataKuliahList
    
    fun addMataKuliah(mataKuliah: MataKuliah) {
        _mataKuliahList.value = _mataKuliahList.value + mataKuliah
    }
    
    fun updateMataKuliah(oldValue: MataKuliah , newValue: MataKuliah) {
        _mataKuliahList.value = _mataKuliahList.value.map {
            if (it.tambahNilai.nama == oldValue.tambahNilai.nama) {
                newValue
            } else {
                it
            }
        }
        Log.d("KelolaNilaiViewModel", "updateMataKuliah: $mataKuliahList")
    }
    
    fun deleteMataKuliah(mataKuliah: MataKuliah) {
        _mataKuliahList.value = _mataKuliahList.value.filter { it.tambahNilai.nama != mataKuliah.tambahNilai.nama }
    }
}