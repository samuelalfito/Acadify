package com.acadify.presentation.kelolanilai

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acadify.model.data.MataKuliah
import com.acadify.model.repository.network.FireFirestore
import com.acadify.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class KelolaNilaiViewModel : ViewModel() {
    private val repository = FireFirestore()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private val _mataKuliahList = Channel<Resource<List<MataKuliah>>>()
    val mataKuliahList = _mataKuliahList.receiveAsFlow()
    
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
    
    fun updateMataKuliah(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            val result = repository.updateMataKuliah(userId, mataKuliah)
            if (result is Resource.Error) {
                Log.e("KelolaNilaiViewModel", "updateMataKuliah: ${result.msg}")
            }
            fetchKelolaNilai()
        }
    }
    
    fun addMataKuliah(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            userId.let {
                val result = repository.addMataKuliah(it, mataKuliah)
                if (result is Resource.Error) {
                    Log.e("KelolaNilaiViewModel", "addMataKuliah: ${result.msg}")
                }
                fetchKelolaNilai()
            }
        }
    }
    
    fun deleteMataKuliah(documentId: String) {
        viewModelScope.launch {
            val result = repository.deleteMataKuliah(userId, documentId)
            if (result is Resource.Error) {
                Log.e("KelolaNilaiViewModel", "deleteMataKuliah: ${result.msg}")
            }
            fetchKelolaNilai()
        }
    }
}