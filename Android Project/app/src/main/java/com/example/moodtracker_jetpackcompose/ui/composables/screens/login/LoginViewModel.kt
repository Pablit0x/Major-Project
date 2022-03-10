package com.example.moodtracker_jetpackcompose.ui.composables.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.BottomBarScreen
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
                navController.navigate(BottomBarScreen.Home.route)
                Log.e("Login", "Logged in successfully")
            }
            .addOnFailureListener {
                //login failed
                Log.e("Login", "Login failed")
            }
    }
}