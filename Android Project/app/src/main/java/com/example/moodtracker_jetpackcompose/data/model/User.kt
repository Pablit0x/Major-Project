package com.example.moodtracker_jetpackcompose.data.model

data class RegularUser(
    val username: String? = null,
    val email: String? = null,
    val userType: String? = null,
    val uid: String? = null
)

data class SupervisorUser(
    val username: String? = null,
    val email: String? = null,
    val userType: String? = null,
    val requests: MutableList<String>? = null,
    val supervised: MutableList<String>? = null,
    val uid: String? = null
)