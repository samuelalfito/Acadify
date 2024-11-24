package com.acadify.model.data

data class KomponenNilai(
    val nilaiTugas: Float = 0f,
    val nilaiKuis: Float = 0f,
    val nilaiUTS: Float = 0f,
    val nilaiUAS: Float = 0f,
    val persentaseTugas: Float = 25f,
    val persentaseKuis: Float = 25f,
    val persentaseUTS: Float = 25f,
    val persentaseUAS: Float = 25f,
) {
    // No-argument constructor for Firestore
    constructor() : this(0f, 0f, 0f, 0f, 25f, 25f, 25f, 25f)
}