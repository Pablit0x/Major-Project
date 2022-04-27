package com.example.better_me.ui.composables.screens.regular.calendar

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.ui.composables.reusable_components.DatePickerView
import com.example.better_me.ui.composables.reusable_components.RegularUserTopBar
import com.example.better_me.ui.composables.reusable_components.UserBottomBar
import com.example.better_me.R


@Composable
fun CalendarScreen(navController: NavHostController) {
    Scaffold(bottomBar = { UserBottomBar(navController = navController) }, topBar = {
        RegularUserTopBar(
            navController = navController,
            title = stringResource(id = R.string.my_calendar),
            showAddIcon = false,
            isHome = false
        )
    }, content = { DatePickerView(navController = navController, userType = REGULAR_USER, "", "") })
}
