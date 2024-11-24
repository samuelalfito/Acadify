package com.acadify.model.repository.network

import com.acadify.model.data.MataKuliah
import com.acadify.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireFirestore() {
    private val firestore = FirebaseFirestore.getInstance()
    
    suspend fun addMataKuliah(userId: String, mataKuliah: MataKuliah): Resource<Void> {
        return try {
            val documentRef = firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document()
            mataKuliah.id = documentRef.id
            documentRef.set(mataKuliah).await()
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    
    suspend fun fetchKelolaNilai(userId: String): Resource<List<MataKuliah>> {
        return try {
            val result = firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").get().await()
            val listMataKuliah = result.toObjects(MataKuliah::class.java)
            Resource.Success(listMataKuliah)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    
    suspend fun updateMataKuliah(userId: String, mataKuliah: MataKuliah): Resource<Void> {
        return try {
            firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document(mataKuliah.id).set(mataKuliah).await()
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    
    suspend fun deleteMataKuliah(userId: String, documentId: String): Resource<Void> {
        return try {
            firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document(documentId).delete().await()
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}