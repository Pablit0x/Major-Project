package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.*

@Composable
fun SupervisorCalendarScreen(navController: NavHostController,username : String,userUID: String) {
    Scaffold(bottomBar = { SupervisorBottomBar(navController = navController) }, topBar = {
        SupervisorUserTopBar(
            navController = navController,
            title = "$username Calendar"
        )
    }, content = {
        DatePickerView(navController = navController, userType = SUPERVISOR_USER, userUID = userUID)
    })
}