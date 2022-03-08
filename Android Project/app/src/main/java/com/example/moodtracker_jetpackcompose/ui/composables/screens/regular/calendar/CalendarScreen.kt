package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.BottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.DatePickerView
import com.google.android.material.datepicker.MaterialDatePicker

private lateinit var calendarViewModel: CalendarViewModel

@Composable
fun CalendarScreen(navController: NavHostController) {
    calendarViewModel = CalendarViewModel()
    Scaffold(bottomBar = { BottomBar(navController = navController) }, content = { DatePickerView()})
}
