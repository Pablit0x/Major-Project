package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import com.example.moodtracker_jetpackcompose.data.model.Activity
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateObserver
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Rating
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.time.LocalDateTime

class RegularMainViewModel : ViewModel() {

    private val db = Firebase.firestore

//    fun readAllActivities(date: String, uid: String, myCallback: (SnapshotStateList<Activity>) -> Unit) {
//        val activities = db.collection("records").document(uid).collection(date)
//        activities.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val list = mutableStateListOf<Activity>()
//                for (document in task.result) {
//                    list.add(document.toObject())
//                }
//                myCallback(list)
//            }
//        }
//    }

//    fun deleteActivity(activity: Activity, uid: String, date: String) {
//        val activityID = activity.id as String
//        db.collection("records").document(uid).collection(date).document(activityID).delete()
//            .addOnSuccessListener { Log.e("del", "DocumentSnapshot successfully deleted!") }
//            .addOnFailureListener { e -> Log.e("del", "Error deleting document", e) }
//    }

//    fun readRating(date: String, uid: String, myCallback: (Int) -> Unit) {
//        val activities = db.collection("records").document(uid).collection("ratings").document(date)
//        activities.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val rating = task.result["rating"]
//                if (rating != null) {
//                    myCallback(rating.toString().toInt())
//                } else {
//                    myCallback(0)
//                }
//            }
//        }
//    }

//    fun saveRating(uid: String, rating: Int, date: String) {
//        val userRating = Rating(rating = rating)
//        db.collection("records").document(uid).collection("ratings").document(date).set(userRating)
//    }

//    fun addActivity(activity: Activity, uid: String, date: String) {
//        val activityID = activity.id as String
//        db.collection("records").document(uid).collection(date).document(activityID).set(activity)
//    }

    fun addSupervisor(email : String){
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

//    fun readFeedback(date: String, uid: String, myCallback: (String) -> Unit){
//        val activities = db.collection("records").document(uid).collection("feedbacks").document(date)
//        activities.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val feedback = task.result["text"]
//                if (feedback != null) {
//                    myCallback(feedback.toString())
//                }
//                else{
//                    myCallback("")
//                }
//            }
//        }
//    }
}