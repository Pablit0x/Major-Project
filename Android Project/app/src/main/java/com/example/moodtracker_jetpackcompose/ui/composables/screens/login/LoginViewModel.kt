package com.example.moodtracker_jetpackcompose.ui.composables.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun firebaseLogin(firebaseAuth: FirebaseAuth, email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.e("Login", "Logged in successfully")
            }
            .addOnFailureListener {
                //login failed
                Log.e("Login", "Login failed")
            }
    }

    fun checkLoginStatus(firebaseAuth: FirebaseAuth) {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // Go to profile Screen
        }
    }
}