package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.widget.CalendarView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite

@Composable
fun DatePickerView(navController: NavController, userType: Int, userUID: String, username: String) {
    var date by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { CalendarView(it) },
            update = {
                it.setOnDateChangeListener { _, year, month, day ->
                    date = "$day-${month + 1}-$year"
                }
            },
            modifier = Modifier.border(
                2.dp,
                color = PerfectWhite,
                shape = RoundedCornerShape(25.dp)
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(PerfectWhite),
            onClick = {
                if (userType == REGULAR_USER) {
                    navController.navigate(Screen.RegularMainScreen.passDate(date = date))
                } else {
                    navController.navigate(
                        Screen.SupervisorViewScreen.passUsernameDateAndUID(
                            username = username,
                            date = date,
                            uid = userUID
                        )
                    )
                }
            },
        ) {
            Text(
                text = stringResource(id = R.string.select),
                fontSize = 20.sp,
                color = PerfectBlack
            )
        }
    }

}