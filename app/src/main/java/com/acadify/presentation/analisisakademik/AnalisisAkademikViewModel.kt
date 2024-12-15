package com.acadify.presentation.analisisakademik

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acadify.model.data.MataKuliah
import com.acadify.model.repository.network.FireFirestore
import com.acadify.utils.ConnectivityChecker
import com.acadify.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AnalisisAkademikViewModel : ViewModel() {
    val repository = FireFirestore()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    
    private val _mataKuliahList = Channel<Resource<List<MataKuliah>>>()
    val mataKuliahList = _mataKuliahList.receiveAsFlow()
    
    fun fetchKelolaNilai(context: Context) {
        viewModelScope.launch {
            if (!ConnectivityChecker.isOnline(context)) {
                _mataKuliahList.send(Resource.Error("No internet connection"))
                return@launch
            }
            
            flow {
                emit(Resource.Loading())
                val result = repository.fetchKelolaNilai(userId)
                if (result is Resource.Success) {
                    val sortedList = result.data?.sortedByDescending { it.tambahNilai.nilai }
                    emit(Resource.Success(sortedList))
                } else {
                    emit(result)
                }
            }.collect {
                _mataKuliahList.send(it)
            }
        }
    }
}