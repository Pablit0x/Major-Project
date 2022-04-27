package com.example.moodtracker_jetpackcompose.data.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Feedback(
    val text: String? = null
)

fun addFeedback(uid: String, text: String, date: String) {
    val db = Firebase.firestore
    val feedback = Feedback(text = text)
    db.collection("records").document(uid).collection("feedbacks").document(date).set(feedback)
}