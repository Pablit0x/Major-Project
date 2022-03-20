package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.add_activity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class AddActivityViewModel : ViewModel(){


    fun addActivity(activity: Activity, uid : String){
        val db = Firebase.firestore
        val currentDate = LocalDateTime.now()
        val activityID = activity.id as String
        val formattedDate = "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        db.collection("records").document(uid).collection(formattedDate).document(activityID).set(activity)
        Log.e("X", formattedDate)
    }
}