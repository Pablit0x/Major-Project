package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Rating
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class FinishDayViewModel : ViewModel() {


    fun saveRating(uid : String, rating : Int){
        val db = Firebase.firestore
        val currentDate = LocalDateTime.now()
        val formattedDate = "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        val userRating = Rating(rating = rating)
        db.collection("records").document(uid).collection("ratings").document(formattedDate).set(userRating)
    }
}