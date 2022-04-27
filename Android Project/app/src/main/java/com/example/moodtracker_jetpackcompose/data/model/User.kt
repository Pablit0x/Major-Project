package com.example.moodtracker_jetpackcompose.data.model

import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class RegularUser(
    val username: String? = null,
    val email: String? = null,
    val userType: String? = null,
    val uid: String? = null,
    val avatarID: Int? = null
)

data class SupervisorUser(
    val username: String? = null,
    val email: String? = null,
    val userType: String? = null,
    val requests: MutableList<String>? = null,
    val supervised: MutableList<String>? = null,
    val uid: String? = null,
    val avatarID: Int? = null
)

fun setAvatar(userType: Int, avatarID: Int) {
    val db = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    when (userType) {
        REGULAR_USER -> {
            db.collection("regularUsers").document(currentUser!!.uid).update("avatarID", avatarID)
        }
        SUPERVISOR_USER -> {
            db.collection("supervisorUsers").document(currentUser!!.uid)
                .update("avatarID", avatarID)
        }
    }
}

fun getAvatarID(userID : String,userType: Int, myCallback: (Int) -> Unit) {
    val db = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    when (userType) {
        REGULAR_USER -> {
            val userRef = db.collection("regularUsers").document(userID)
            userRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val avatarID = task.result["avatarID"]
                    if (avatarID != null) {
                        myCallback(avatarID.toString().toInt())
                    } else {
                        myCallback(-1)
                    }
                }
            }
        }
        SUPERVISOR_USER -> {
            val userRef = db.collection("supervisorUsers").document(currentUser!!.uid)
            userRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val avatarID = task.result["avatarID"]
                    if (avatarID != null) {
                        myCallback(avatarID.toString().toInt())
                    } else {
                        myCallback(-1)
                    }
                }
            }
        }
    }
}