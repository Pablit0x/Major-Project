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
import com.example.moodtracker_jetpackcompose.ui.theme.SourceSerifPro
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.tertiaryColor

@Composable
fun ActivityTypeField(activityType : MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
//    var selectedType by remember{ mutableStateOf()}
//
//    val activityTypes : Map<String, ImageVector> = mapOf(
//        ActivityType.Sleep.name to ActivityType.Sleep.icon,
//        ActivityType.Work.name to ActivityType.Work.icon,
//        ActivityType.Study.name to ActivityType.Study.icon,
//        ActivityType.Meditation.name to ActivityType.Meditation.icon,
//        ActivityType.Workout.name to ActivityType.Workout.icon,
//        ActivityType.Other.name to ActivityType.Other.icon
//    )
    val activityTypes = listOf(
        ActivityType.Sleep.name,
        ActivityType.Work.name,
        ActivityType.Study.name,
        ActivityType.Meditation.name,
        ActivityType.Workout.name,
        ActivityType.Other.name
    )

//    val activityIcons = listOf(
//        Icons.Default.AccountBox,
//        Icons.Default.AccountCircle,
//        Icons.Default.Send,
//        Icons.Default.Favorite,
//        Icons.Default.Email,
//        Icons.Default.Build
//    )
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        OutlinedTextField(
            value = activityType.value,
            onValueChange = { accountTypeValue ->
                activityType.value = accountTypeValue
            },
            enabled = false,
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .onGloballyPositioned
                { coords ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coords.size.toSize()
                },
            placeholder = { Text(text = "Select Activity Type") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
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