package com.acadify.presentation.prediksiip

import androidx.lifecycle.ViewModel
import com.acadify.model.data.PrediksiIP
import com.acadify.presentation.kelolanilai.KelolaNilaiViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PrediksiIPViewModel : ViewModel() {
    val mataKuliahList = KelolaNilaiViewModel().mataKuliahList
    
    private var _prediksiIP = MutableStateFlow(PrediksiIP(emptyList(), emptyList(), 0f))
    val prediksiIP: StateFlow<PrediksiIP> = _prediksiIP
    
    fun hitungPrediksiIP() {
        val updatedPrediksi = mutableListOf<PrediksiItem>()
        var totalNilaiAkhir = 0f
        
        mataKuliahList.value.forEach { mataKuliah ->
            mataKuliah.komponenNilai?.let { komponenNilai ->
                val nilaiTugas = komponenNilai.nilaiTugas * komponenNilai.persentaseTugas
                val nilaiKuis = komponenNilai.nilaiKuis * komponenNilai.persentaseKuis
                val nilaiUTS = komponenNilai.nilaiUTS * komponenNilai.persentaseUTS
                val nilaiUAS = komponenNilai.nilaiUAS * komponenNilai.persentaseUAS
                val totalNilai = nilaiTugas + nilaiKuis + nilaiUTS + nilaiUAS
                
                updatedPrediksi.add(PrediksiItem(mataKuliah.tambahNilai.nama, totalNilai))
                totalNilaiAkhir += totalNilai
            }
        }
        
        val averageNilaiAkhir = if (mataKuliahList.value.isNotEmpty()) {
            totalNilaiAkhir / mataKuliahList.value.size
        } else {
            0f
        }
        
        _prediksiIP.value = PrediksiIP(
            nama = updatedPrediksi.map { it.nama },
            nilaiAkhir = updatedPrediksi.map { it.nilaiAkhir },
            prediksiIP = averageNilaiAkhir
        )
    }
}

data class PrediksiItem(
    val nama: String,
    val nilaiAkhir: Float
)