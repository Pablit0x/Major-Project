package com.example.moodtracker_jetpackcompose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class UserBottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Calendar : UserBottomBarScreen(
        route = "calendar",
        title = "Calendar",
        icon = Icons.Default.DateRange
    )

    object Home : UserBottomBarScreen(
        route = "home/user",
        title = "Home",
        icon = Icons.Default.Home
    )

//    object Messenger : UserBottomBarScreen(
//        route = "messenger",
//        title = "Messenger",
//        icon = Icons.Default.Email
//    )
}
