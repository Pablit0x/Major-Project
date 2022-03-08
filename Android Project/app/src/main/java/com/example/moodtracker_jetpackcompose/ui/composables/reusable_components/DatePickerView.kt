package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DatePickerView() {
    var date by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = { CalendarView(it)}, update = {
            it.setOnDateChangeListener{ _, year, month, day ->
                date = "$day / $month / $year"
            }
        })
        Text(text = date)
        Spacer(modifier = Modifier.padding(10.dp))
        Button(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp),
            onClick = { /*TODO*/ },) {
            Text(text = "Select", fontSize = 20.sp)
        }
    }

}