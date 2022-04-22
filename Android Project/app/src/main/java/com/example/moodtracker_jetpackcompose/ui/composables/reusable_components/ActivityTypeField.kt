package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.data.model.ActivityType
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor

@Composable
fun ActivityTypeField(activityType: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }


    val activityTypes = listOf(
        ActivityType.Sleep.name,
        ActivityType.Work.name,
        ActivityType.Study.name,
        ActivityType.Meditation.name,
        ActivityType.Workout.name,
        ActivityType.Other.name
    )

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        TextField(
            value = activityType.value,
            onValueChange = { accountTypeValue ->
                activityType.value = accountTypeValue
            },
            enabled = false,
//            textStyle = TextStyle(
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            ),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.White,
                backgroundColor = Color(0xFF191919)
            ),
//            colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned
                { coords ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coords.size.toSize()
                },
            placeholder = { Text(text = "Select Activity Type...", color = Color.LightGray) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded }, tint = Color.LightGray)
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            activityTypes.forEach { name ->
                DropdownMenuItem(onClick = {
                    activityType.value = name
                    expanded = false
                }) {
                    Text(text = name, textAlign = TextAlign.Center)
                }
            }
        }
    }
}