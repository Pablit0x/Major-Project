package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.add_supervisor

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.firebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddSupervisorViewModel : ViewModel() {


    private val db = Firebase.firestore

    fun addSupervisor(email: String) {
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

    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }
}