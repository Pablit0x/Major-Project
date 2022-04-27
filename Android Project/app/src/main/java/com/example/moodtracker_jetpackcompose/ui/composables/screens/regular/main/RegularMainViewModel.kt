package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RegularMainViewModel : ViewModel() {

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

    fun getUserSupervisors(myCallback: (SnapshotStateList<SupervisorUser>) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val userSupervisors = db.collection("supervisorUsers")
            .whereArrayContains("supervised", firebaseAuth.currentUser!!.uid)
        userSupervisors.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val users = mutableStateListOf<SupervisorUser>()
                for (document in task.result) {
                    users.add(document.toObject())
                }
                myCallback(users)
            }
        }
    }

    fun deleteSupervisor(supervisorID: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val supervisorDoc = db.collection("supervisorUsers").document(supervisorID)
        supervisorDoc.update("supervised", FieldValue.arrayRemove(firebaseAuth.currentUser!!.uid))
    }
}