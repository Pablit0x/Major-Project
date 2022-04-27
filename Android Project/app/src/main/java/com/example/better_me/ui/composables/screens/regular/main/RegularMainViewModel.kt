package com.example.better_me.ui.composables.screens.regular.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Activity
import com.example.better_me.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RegularMainViewModel : ViewModel() {

    private val db = Firebase.firestore

    fun deleteActivity(activity: Activity, uid: String, date: String) {
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).delete()
            .addOnSuccessListener { Log.e("del", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.e("del", "Error deleting document", e) }
    }

    fun setActivityStatus(done: Boolean, activity: Activity, date: String) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val activityID = activity.id as String
        val db = Firebase.firestore
        val documentRef =
            db.collection("records").document(uid).collection(date).document(activityID)
        documentRef.update("done", done)
    }


    fun getAllActivities(
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