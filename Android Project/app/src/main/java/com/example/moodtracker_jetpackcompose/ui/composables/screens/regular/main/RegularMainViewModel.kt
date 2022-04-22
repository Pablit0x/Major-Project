package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegularMainViewModel : ViewModel() {

    private val db = Firebase.firestore

    fun addSupervisor(email: String) {
        FirebaseAuth.getInstance()
        db.collection("supervisorUsers").whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val updatedRequests = mutableListOf<String>()
                    (document["requests"] as MutableList<String>?)?.let { updatedRequests.addAll(it) }
                    updatedRequests.add(firebaseAuth.currentUser?.uid.toString())


                    val updatedDoc = SupervisorUser(
                        username = document["username"].toString(),
                        email = document["email"].toString(),
                        userType = document["userType"].toString(),
                        uid = document["uid"].toString(),
                        requests = updatedRequests,
                        supervised = document["supervised"] as MutableList<String>?
                    )
                    db.collection("supervisorUsers").document(document["uid"].toString())
                        .set(updatedDoc)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("fail", exception.toString())
            }
    }
}