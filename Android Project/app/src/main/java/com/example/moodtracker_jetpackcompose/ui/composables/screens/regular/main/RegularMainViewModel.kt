package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import com.example.moodtracker_jetpackcompose.data.model.Activity
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateObserver
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Rating
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document
import java.time.LocalDateTime
import kotlin.math.log

class RegularMainViewModel : ViewModel() {

    fun readData(date: String, uid: String, myCallback: (SnapshotStateList<Activity>) -> Unit) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list = mutableStateListOf<Activity>()
                for (document in task.result) {
                    list.add(document.toObject())
                }
                myCallback(list)
            }
        }
    }

    fun deleteActivity(activity: Activity, uid: String, date: String){
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).delete()
            .addOnSuccessListener { Log.e("del", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.e("del", "Error deleting document", e) }
    }

    fun readRating(date: String, uid: String, myCallback: (Int?) -> Unit) {
        val db = Firebase.firestore
        val activities = db.collection("records").document(uid).collection("ratings").document(date)
        activities.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val rating = task.result["rating"]
                if(rating != null){
                    myCallback(rating.toString().toInt())
                }
                else{
                    myCallback(null)
                }
            }
        }
    }

    fun saveRating(uid: String, rating: Int, date: String) {
        val db = Firebase.firestore
        val userRating = Rating(rating = rating)
        db.collection("records").document(uid).collection("ratings").document(date).set(userRating)
    }

    fun addActivity(activity: Activity, uid: String, date: String) {
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).set(activity)
        Log.e("X", getCurrentDate())
    }


    private fun getCurrentDate(): String {
        val currentDate = LocalDateTime.now()
        return "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
    }
}