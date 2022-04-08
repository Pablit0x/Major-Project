package com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpViewModel : ViewModel() {

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }

    fun validateUsername(username: String): Boolean {
        return username.isNotEmpty()
    }

    fun validateUserType(userType: String): Boolean {
        return userType.isNotEmpty()
    }


    fun firebaseSignUp(
        firebaseAuth: FirebaseAuth,
        username: String,
        email: String,
        userType: String,
        password: String,
        navController: NavController
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val firebaseUser = firebaseAuth.currentUser!!
                        navController.navigate(Screen.LoginScreen.route)
                        val db = Firebase.firestore
                        when (userType) {
                            "Regular" ->
                            {
                                val user = RegularUser(username = username, email = email, userType = userType, uid = firebaseUser.uid)
                                db.collection("regularUsers").document(firebaseUser.uid).set(user)
                            }
                            "Supervisor" ->
                            {
                                val user = SupervisorUser(username = username, email = email, userType = userType, uid = firebaseUser.uid)
                                db.collection("supervisorUsers").document(firebaseUser.uid).set(user)
                            }
                        }
                    }
            }
            .addOnFailureListener {
                Log.e("Sign Up", "Fail")
            }
    }
}