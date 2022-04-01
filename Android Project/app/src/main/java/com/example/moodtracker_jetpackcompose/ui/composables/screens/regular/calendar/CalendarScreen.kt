package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.BottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.DatePickerView
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RegularUserTopBar

private lateinit var calendarViewModel: CalendarViewModel

@Composable
fun CalendarScreen(navController: NavHostController) {
    calendarViewModel = CalendarViewModel()
    Scaffold(bottomBar = { BottomBar(navController = navController) }, topBar = { RegularUserTopBar(
        navController = navController,
        title = "Calendar"
    )}, content = { DatePickerView(navController = navController)})
}
