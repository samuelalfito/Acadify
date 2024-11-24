package com.acadify.model.data

data class KomponenNilai(
    val nilaiTugas: Float = 0f,
    val nilaiKuis: Float = 0f,
    val nilaiUTS: Float = 0f,
    val nilaiUAS: Float = 0f,
    val persentaseTugas: Int = 25,
    val persentaseKuis: Int = 25,
    val persentaseUTS: Int = 25,
    val persentaseUAS: Int = 25,
) {
    // No-argument constructor for Firestore
    constructor() : this(0f, 0f, 0f, 0f, 25, 25, 25, 25)
}