package com.acadify.model.data

data class MataKuliah(
    var id: String = "",
    val tambahNilai: TambahNilai = TambahNilai(),
    val komponenNilai: KomponenNilai? = KomponenNilai()
) {
    // No-argument constructor for Firestore
    constructor() : this("", TambahNilai(), KomponenNilai())
}