package com.example.better_me.ui.composables.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.better_me.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun firebaseLogin(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val db = Firebase.firestore
                val doc = db.collection("regularUsers")
                    .whereEqualTo("email", firebaseAuth.currentUser!!.email)
                doc.get().addOnSuccessListener { documents ->
                    if (documents.size() == 1) {
                        navController.navigate(Screen.RegularMainScreen.route)
                    } else {
                        navController.navigate(Screen.SupervisorMainScreen.route)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("Login", "Login failed")
            }
    }


    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }
}