package com.acadify.model.repository.network

import com.acadify.model.data.MataKuliah
import com.acadify.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FireFirestore() {
    private val firestore = FirebaseFirestore.getInstance()
    
    suspend fun addMataKuliah(userId: String, mataKuliah: MataKuliah): Resource<Void> {
        return try {
            val counterDoc = firestore.collection("KelolaNilai").document(userId)
            val counterSnapshot = counterDoc.get().await()
            val counter = if (counterSnapshot.exists()) {
                counterSnapshot.getLong("counter") ?: 0
            } else {
                0
            }
            val newCounter = counter + 1
            counterDoc.set(mapOf("counter" to newCounter)).await()
            
            val documentId = "$newCounter"
            firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document(documentId).set(mataKuliah).await()
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
    
    suspend fun updateMataKuliah(userId: String, documentId: String, newValue: MataKuliah): Resource<Void> {
        return try {
            firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document(documentId).set(newValue).await()
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
    
    suspend fun deleteMataKuliah(userId: String, documentId: String): Resource<Void> {
        return try {
            val counterDoc = firestore.collection("KelolaNilai").document(userId)
            val counterSnapshot = counterDoc.get().await()
            val counter = if (counterSnapshot.exists()) {
                counterSnapshot.getLong("counter") ?: 0
            } else {
                0
            }
            val newCounter = counter - 1
            counterDoc.set(mapOf("counter" to newCounter)).await()
            
            firestore.collection("KelolaNilai").document(userId).collection("MataKuliah").document(documentId).delete().await()
            Resource.Success(null)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}