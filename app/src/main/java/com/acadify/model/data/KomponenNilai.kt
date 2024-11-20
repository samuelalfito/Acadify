package com.acadify.model.data

data class KomponenNilai(
    val nilaiTugas: Float,
    val nilaiKuis: Float,
    val nilaiUTS: Float,
    val nilaiUAS: Float,
    val persentaseTugas: Float = 0.25f,
    val persentaseKuis: Float = 0.25f,
    val persentaseUTS: Float = 0.25f,
    val persentaseUAS: Float = 0.25f
)