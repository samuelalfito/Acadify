package com.acadify.model.repository.network

import com.google.firebase.auth.FirebaseAuth

class FireAuth {
    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }
    
    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }
    
    fun logout() {
        auth.signOut()
    }
    
    fun getCurrentUser() = auth.currentUser
}