package com.example.moodtracker_jetpackcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Calendar : BottomBarScreen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.DateRange
    )

    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Messenger : BottomBarScreen(
        route = "messenger",
        title = "Messenger",
        icon = Icons.Default.Email
    )
}
