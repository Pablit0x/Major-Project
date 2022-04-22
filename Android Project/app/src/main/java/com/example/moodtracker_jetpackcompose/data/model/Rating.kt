package com.example.moodtracker_jetpackcompose.data.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Rating(
    val rating: Int? = null
)

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

fun saveRating(uid: String, rating: Int, date: String) {
    val db = Firebase.firestore
    val userRating = Rating(rating = rating)
    db.collection("records").document(uid).collection("ratings").document(date).set(userRating)
}