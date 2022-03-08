package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.BottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.DatePickerView

private lateinit var calendarViewModel: CalendarViewModel

@Composable
fun CalendarScreen(navController: NavHostController) {
    calendarViewModel = CalendarViewModel()
    Scaffold(bottomBar = { BottomBar(navController = navController) }, content = { DatePickerView()})
}
