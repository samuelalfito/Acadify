package com.acadify.presentation.simulasinilaiipk

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acadify.model.data.MataKuliah
import com.acadify.model.repository.network.FireFirestore
import com.acadify.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SimulasiNilaiIPKViewModel : ViewModel() {
    private val repository = FireFirestore()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    
    private val _mataKuliahList = Channel<Resource<List<MataKuliah>>>()
    val mataKuliahList = _mataKuliahList.receiveAsFlow()
    
    private val _simulasiMataKuliah = MutableStateFlow(emptyList<MataKuliah>())
    val simulasiMataKuliah: StateFlow<List<MataKuliah>> = _simulasiMataKuliah
    
    val targetIPK: StateFlow<Float> = MutableStateFlow(4.0f)
    
    fun fetchKelolaNilai() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                emit(repository.fetchKelolaNilai(userId))
            }.collect {
                _mataKuliahList.send(it)
            }
        }
    }
    
    fun addMataKuliah(mataKuliah: MataKuliah) {
        _simulasiMataKuliah.value = _simulasiMataKuliah.value + mataKuliah
    }
    
    fun updateMataKuliah(oldValue: MataKuliah , newValue: MataKuliah) {
        _simulasiMataKuliah.value = _simulasiMataKuliah.value.map {
            if (it.tambahNilai.nama == oldValue.tambahNilai.nama) {
                newValue
            } else {
                it
            }
        }
        Log.d("KelolaNilaiViewModel", "updateMataKuliah: $mataKuliahList")
    }
    
    fun deleteMataKuliah(mataKuliah: MataKuliah) {
        _simulasiMataKuliah.value = _simulasiMataKuliah.value.filter { it.tambahNilai.nama != mataKuliah.tambahNilai.nama }
    }
    
    fun updateTargetIPK(targetIPK: Float) {
        (this.targetIPK as MutableStateFlow).value = targetIPK
    }
    
    fun hitungIPK(mataKuliah: List<MataKuliah>?): Float {
        if (mataKuliah == null) return 0f
        var totalSKS = 0f
        var totalNilai = 0f
        mataKuliah.forEach {
            totalSKS += it.tambahNilai.jumlahSKS
            totalNilai += it.tambahNilai.nilai * it.tambahNilai.jumlahSKS
        }
        _simulasiMataKuliah.value.forEach {
            totalSKS += it.tambahNilai.jumlahSKS
            totalNilai += it.tambahNilai.nilai * it.tambahNilai.jumlahSKS
        }
        val ipk = totalNilai / totalSKS
        Log.d("SimulasiNilaiIPKViewModel", "hitungIPK: $ipk")
        return ipk
    }
}