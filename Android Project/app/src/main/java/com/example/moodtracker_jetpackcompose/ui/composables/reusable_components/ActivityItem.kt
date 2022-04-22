package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.data.model.setActivityStatus
import com.example.moodtracker_jetpackcompose.ui.theme.IconSizes
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.tertiaryColor

@Composable
fun ListItem(activity: Activity, date: String, userType: Int) {
    val regularTextStyle = TextStyle(textDecoration = TextDecoration.None)
    val crossedTextStyle = TextStyle(textDecoration = TextDecoration.LineThrough)
    var style by remember { mutableStateOf(regularTextStyle) }
    var bgColor by remember { mutableStateOf(Color.White) }
    var textAlignment by remember { mutableStateOf(TextAlign.Center) }
    var dividerColor by remember { mutableStateOf(Color.LightGray) }
    var checkState by remember { mutableStateOf(activity.done.toString().toBoolean()) }
    var icon by remember { mutableStateOf(R.drawable.ic_other_ac) }

    when (activity.type) {
        "Study" -> icon = R.drawable.ic_study_ac
        "Sleep" -> icon = R.drawable.ic_sleep_ac
        "Work" -> icon = R.drawable.ic_work_ac
        "Workout" -> icon = R.drawable.ic_exercise_ac
        "Meditation" -> icon = R.drawable.ic_meditation_ac
        "Other" -> icon - R.drawable.ic_other_ac
    }

    when (userType) {
        REGULAR_USER -> textAlignment = TextAlign.Center
        SUPERVISOR_USER -> textAlignment = TextAlign.Start
    }

    when (activity.createdBy) {
        REGULAR_USER -> bgColor = Color.White
        SUPERVISOR_USER -> bgColor = tertiaryColor
    }

    when (checkState) {
        true -> {
            style = crossedTextStyle
            bgColor = Color.LightGray
            dividerColor = Color.White
        }
        false -> {
            style = regularTextStyle
            dividerColor = Color.LightGray
        }
    }

    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .background(color = bgColor)
                .fillMaxHeight(0.3f)
                .defaultMinSize(minHeight = 80.dp)
                .fillMaxWidth()
        ) {
            Icon(
                painter = (painterResource(id = icon)), contentDescription = "", modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .size(
                        IconSizes.medium
                    )
            )
            Text(
                text = activity.name.toString(),
                textAlign = textAlignment,
                style = style,
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(4f)
                    .fillMaxHeight()
            )
            if (userType == REGULAR_USER) {
                Checkbox(
                    checked = checkState, onCheckedChange = {
                        checkState = it
                        setActivityStatus(checkState, activity, date = date)
                    }, modifier = Modifier
                        .scale(1.5f)
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CheckboxDefaults.colors(secondaryColor)
                )
            }
        }
        Divider(color = dividerColor, modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
    }
}