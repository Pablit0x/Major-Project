package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.DatePickerView
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserTopBar

@Composable
fun SupervisorCalendarScreen(navController: NavHostController, username: String, userUID: String) {
    Scaffold(bottomBar = { SupervisorBottomBar(navController = navController) }, topBar = {
        SupervisorUserTopBar(
            navController = navController,
            title = "$username Calendar",
            isHome = false
        )
    }, content = {
        DatePickerView(
            navController = navController,
            userType = SUPERVISOR_USER,
            userUID = userUID,
            username = username
        )
    })
}