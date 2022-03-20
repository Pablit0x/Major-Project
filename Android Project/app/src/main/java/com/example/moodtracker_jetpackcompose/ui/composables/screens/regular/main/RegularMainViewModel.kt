package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import com.example.moodtracker_jetpackcompose.data.model.Activity
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RegularMainViewModel : ViewModel() {

    fun readData(date : String,uid : String, myCallback: (SnapshotStateList<Activity>) -> Unit) {
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
}