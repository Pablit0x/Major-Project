package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.DatePickerView
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RegularUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserBottomBar


@Composable
fun CalendarScreen(navController: NavHostController) {
    Scaffold(bottomBar = { UserBottomBar(navController = navController) }, topBar = {
        RegularUserTopBar(
            navController = navController,
            title = stringResource(id = R.string.my_calendar)
        )
    }, content = { DatePickerView(navController = navController, userType = REGULAR_USER, "", "") })
}
