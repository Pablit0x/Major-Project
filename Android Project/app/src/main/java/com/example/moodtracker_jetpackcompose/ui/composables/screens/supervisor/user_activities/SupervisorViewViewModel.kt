package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.user_activities

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SupervisorViewViewModel : ViewModel() {
    fun getPublicActivities(
        date: String,
        uid: String,
        myCallback: (SnapshotStateList<Activity>) -> Unit
    ) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection(date)
        activities.orderBy("dateCreated").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableStateListOf<Activity>()
                for (document in task.result) {
                    if (document["privacyType"] == "Public")
                        list.add(document.toObject())
                }
                myCallback(list)
            }
        }
    }

    fun getFeedback(date: String, uid: String, myCallback: (String) -> Unit) {
        val db = Firebase.firestore
        val activities =
            db.collection("records").document(uid).collection("feedbacks").document(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val feedback = task.result["text"]
                if (feedback != null) {
                    myCallback(feedback.toString())
                } else {
                    myCallback("")
                }
            }
        }
    }

    fun getRating(date: String, uid: String, myCallback: (Int) -> Unit) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection("ratings").document(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val rating = task.result["rating"]
                if (rating != null) {
                    myCallback(rating.toString().toInt())
                } else {
                    myCallback(0)
                }
            }
        }
    }
}