package com.example.moodtracker_jetpackcompose.data.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*

data class Activity(
    val name: String? = null,
    val type: String? = null,
    val done: Boolean? = null,
    val id : String? = null,
    val createdBy : Int? = null,
    val privacyType : String? = null,
    @ServerTimestamp
    val dateCreated : Date? = null
)

fun addActivity(activity: Activity, uid: String, date: String) {
    val db = Firebase.firestore
    val activityID = activity.id as String
    db.collection("records").document(uid).collection(date).document(activityID).set(activity)
}

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
    val documentRef = db.collection("records").document(uid).collection(date).document(activityID)
    documentRef.update("done", done)
}

fun getAllActivities(date: String, uid: String, myCallback: (SnapshotStateList<Activity>) -> Unit) {
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

fun getPublicActivities(date: String, uid: String, myCallback: (SnapshotStateList<Activity>) -> Unit) {
    val db = Firebase.firestore
    val activities = db.collection("records").document(uid).collection(date)
    activities.orderBy("dateCreated").get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val list = mutableStateListOf<Activity>()
            for (document in task.result) {
                if(document["privacyType"] == "Public")
                    list.add(document.toObject())
            }
            myCallback(list)
        }
    }
}

