package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SupervisorMainViewModel : ViewModel() {

    fun getSupervisedUsers(myCallback: (SnapshotStateList<RegularUser?>) -> Unit) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentSupervisor = firebaseAuth.currentUser
        val db = Firebase.firestore
        val supervisedUsers = db.collection("supervisorUsers").document(currentSupervisor!!.uid)
        val users = mutableStateListOf<RegularUser?>()
        supervisedUsers.get().addOnSuccessListener { documents ->
            val userUIDs = (documents.data?.get("supervised")) as? MutableList<*>
            if (userUIDs != null) {
                for (uid in userUIDs) {
                    db.collection("regularUsers").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    users.add(document.toObject())
                                    users.sortBy { it!!.username }
                                }
                                myCallback(users)
                            }
                        }
                }
            }
        }
    }
}