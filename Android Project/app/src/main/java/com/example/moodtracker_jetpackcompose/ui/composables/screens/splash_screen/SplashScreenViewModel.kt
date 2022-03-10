package com.example.moodtracker_jetpackcompose.ui.composables.screens.splash_screen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashScreenViewModel : ViewModel() {


    fun checkLoginStatus(firebaseAuth: FirebaseAuth, navController: NavController) {
        val db = Firebase.firestore
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            val doc = db.collection("regularUsers").whereEqualTo("email", firebaseUser.email)
            doc.get().addOnSuccessListener { documents ->
                if(documents.size() == 1){
                    navController.navigate(Screen.RegularMainScreen.route)
                }
                else{
                    navController.navigate(Screen.SupervisorMainScreen.route)
                }
            }
        }
        else{
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}