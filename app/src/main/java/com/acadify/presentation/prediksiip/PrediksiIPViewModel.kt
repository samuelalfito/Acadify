package com.acadify.presentation.prediksiip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acadify.model.data.MataKuliah
import com.acadify.model.data.PrediksiIP
import com.acadify.model.repository.network.FireFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.acadify.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow

class PrediksiIPViewModel : ViewModel() {
    private val repository = FireFirestore()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    
    private val _mataKuliahList = Channel<Resource<List<MataKuliah>>>()
    val mataKuliahList = _mataKuliahList.receiveAsFlow()
    
    private var _prediksiIP = MutableStateFlow(PrediksiIP(emptyList(), emptyList(), 0f))
    val prediksiIP: StateFlow<PrediksiIP> = _prediksiIP
    
    fun hitungPrediksiIP() {
        viewModelScope.launch {
            flow {
                emit(Resource.Loading())
                emit(repository.fetchKelolaNilai(userId))
            }.collect { resource ->
                _mataKuliahList.send(resource)
                if (resource is Resource.Success) {
                    val updatedPrediksi = mutableListOf<PrediksiItem>()
                    var totalNilaiAkhir = 0f
                    
                    resource.data?.forEach { mataKuliah ->
                        mataKuliah.komponenNilai?.let { komponenNilai ->
                            val nilaiTugas =
                                komponenNilai.nilaiTugas * komponenNilai.persentaseTugas / 100
                            val nilaiKuis = komponenNilai.nilaiKuis * komponenNilai.persentaseKuis / 100
                            val nilaiUTS = komponenNilai.nilaiUTS * komponenNilai.persentaseUTS / 100
                            val nilaiUAS = komponenNilai.nilaiUAS * komponenNilai.persentaseUAS / 100
                            val totalNilai = nilaiTugas + nilaiKuis + nilaiUTS + nilaiUAS
                            
                            updatedPrediksi.add(
                                PrediksiItem(
                                    mataKuliah.tambahNilai.nama,
                                    totalNilai
                                )
                            )
                            totalNilaiAkhir += totalNilai
                        }
                    }
                    
                    val averageNilaiAkhir = if (resource.data?.isNotEmpty() == true) {
                        totalNilaiAkhir / resource.data.size
                    } else {
                        0f
                    }
                    
                    _prediksiIP.value = PrediksiIP(
                        nama = updatedPrediksi.map { it.nama },
                        nilaiAkhir = updatedPrediksi.map { it.nilaiAkhir / 25 },
                        prediksiIP = averageNilaiAkhir / 25
                    )
                }
            }
        }
    }
}

data class PrediksiItem(
    val nama: String,
    val nilaiAkhir: Float,
)