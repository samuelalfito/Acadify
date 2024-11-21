package com.acadify.model.data

data class KomponenNilai(
    val nilaiTugas: Float = 0f,
    val nilaiKuis: Float = 0f,
    val nilaiUTS: Float = 0f,
    val nilaiUAS: Float = 0f,
    val persentaseTugas: Float = 0.25f,
    val persentaseKuis: Float = 0.25f,
    val persentaseUTS: Float = 0.25f,
    val persentaseUAS: Float = 0.25f
) {
    // No-argument constructor for Firestore
    constructor() : this(0f, 0f, 0f, 0f, 0.25f, 0.25f, 0.25f, 0.25f)
}