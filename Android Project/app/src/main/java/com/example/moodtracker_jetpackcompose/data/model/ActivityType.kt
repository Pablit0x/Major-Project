package com.example.moodtracker_jetpackcompose.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ActivityType(
    val name: String,
    val icon: ImageVector,
) {

    object None : ActivityType(
        name = "None",
        icon = Icons.Default.Clear
    )

    object Sleep : ActivityType(
        name = "Sleep",
        icon = Icons.Default.DateRange
    )

    object Work : ActivityType(
        name = "Work",
        icon = Icons.Default.Home
    )

    object Meditation : ActivityType(
        name = "Meditation",
        icon = Icons.Default.Email
    )

    object Workout : ActivityType(
        name = "Workout",
        icon = Icons.Default.Person
    )

    object Study : ActivityType(
        name = "Study",
        icon = Icons.Default.AccountBox
    )

    object Other : ActivityType(
        name = "Other Activity",
        icon = Icons.Default.Call
    )
}