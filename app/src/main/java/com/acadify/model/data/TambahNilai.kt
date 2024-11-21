package com.acadify.model.data

data class TambahNilai(
    val nama: String = "",
    val nilai: Float = 0f,
    val jumlahSKS: Float = 0f
) {
    // No-argument constructor for Firestore
    constructor() : this("", 0f, 0f)
}