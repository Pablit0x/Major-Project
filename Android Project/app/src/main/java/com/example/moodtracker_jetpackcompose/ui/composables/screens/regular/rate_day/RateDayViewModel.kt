package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.rate_day

import androidx.lifecycle.ViewModel
import com.example.moodtracker_jetpackcompose.data.model.Rating
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RateDayViewModel : ViewModel() {
    fun saveRating(uid: String, rating: Int, date: String) {
        val db = Firebase.firestore
        val userRating = Rating(rating = rating)
        db.collection("records").document(uid).collection("ratings").document(date).set(userRating)
    }
}