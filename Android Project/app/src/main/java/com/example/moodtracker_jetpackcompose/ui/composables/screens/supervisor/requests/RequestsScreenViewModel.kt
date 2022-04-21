package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.requests

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RequestsScreenViewModel : ViewModel() {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val currentSupervisor = firebaseAuth.currentUser
    private val db = Firebase.firestore
    private val requests = db.collection("supervisorUsers").document(currentSupervisor!!.uid)

    fun getRequests(myCallback: (SnapshotStateList<RegularUser>) -> Unit) {
        val users = mutableStateListOf<RegularUser>()
        requests.get().addOnSuccessListener { documents ->
            val userUIDs = (documents.data?.get("requests")) as? MutableList<*>
            if (userUIDs != null) {
                for (uid in userUIDs) {
                    db.collection("regularUsers").whereEqualTo("uid", uid).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (document in task.result) {
                                    users.add(document.toObject())
                                }
                                myCallback(users)
                            }
                        }
                }
            }
        }
    }

    fun acceptRequest(uid : String){
        requests.update("requests", FieldValue.arrayRemove(uid))
        requests.update("supervised", FieldValue.arrayUnion(uid))
    }

    fun declineRequest(uid : String){
        requests.update("requests", FieldValue.arrayRemove(uid))
    }
}