package com.example.moodtracker_jetpackcompose.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ActivityType(
    val name: String,
) {

    object Sleep : ActivityType(name = "Sleep")

    object Work : ActivityType(
        name = "Work")

    object Meditation : ActivityType(
        name = "Meditation")

    object Workout : ActivityType(
        name = "Workout")

    object Study : ActivityType(
        name = "Study")

    object Other : ActivityType(
        name = "Other Activity")
}