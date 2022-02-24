package com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun validateUsername(username: String): Boolean {
        return username.isNotEmpty()
    }

    fun validateUserType(userType: String): Boolean {
        return userType.isNotEmpty()
    }

    fun firebaseSignUp(
        firebaseAuth: FirebaseAuth,
        email: String,
        password: String,
        navController: NavController
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                navController.navigate(Screen.LoginScreen.route)
                Log.e("Sign Up", "UserCreated")
            }
            .addOnFailureListener {
                Log.e("Sign Up", "Fail")
            }
    }
}