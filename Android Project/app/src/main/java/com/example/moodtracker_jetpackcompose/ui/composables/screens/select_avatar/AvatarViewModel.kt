package com.example.moodtracker_jetpackcompose.ui.composables.screens.select_avatar

import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AvatarViewModel : ViewModel() {
    fun setAvatar(userType: Int, avatarID: Int) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        when (userType) {
            Constants.REGULAR_USER -> {
                db.collection("regularUsers").document(currentUser!!.uid)
                    .update("avatarID", avatarID)
            }
            Constants.SUPERVISOR_USER -> {
                db.collection("supervisorUsers").document(currentUser!!.uid)
                    .update("avatarID", avatarID)
            }
        }
    }

    fun getAvatarID(userID: String, userType: Int, myCallback: (Int) -> Unit) {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().currentUser
        when (userType) {
            Constants.REGULAR_USER -> {
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
            Constants.SUPERVISOR_USER -> {
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
}