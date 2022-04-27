package com.example.better_me.ui.composables.screens.regular.add_activity

import androidx.lifecycle.ViewModel
import com.example.better_me.data.model.Activity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddActivityViewModel : ViewModel() {
    fun validateActivityName(activityName: String): Boolean {
        return activityName.isNotEmpty()
    }

    fun validateActivityType(activityType: String): Boolean {
        return activityType.isNotEmpty()
    }

    fun addActivity(activity: Activity, uid: String, date: String) {
        val db = Firebase.firestore
        val activityID = activity.id as String
        db.collection("records").document(uid).collection(date).document(activityID).set(activity)
    }
}