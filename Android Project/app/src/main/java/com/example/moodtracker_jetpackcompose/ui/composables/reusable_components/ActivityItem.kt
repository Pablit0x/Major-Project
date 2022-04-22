package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.moodtracker_jetpackcompose.ui.theme.GoldenYellow
import com.example.moodtracker_jetpackcompose.ui.theme.IconSizes
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite

@Composable
fun ActivityItem(activity: Activity, date: String, userType: Int) {
    val regularTextStyle = TextStyle(textDecoration = TextDecoration.None)
    val crossedTextStyle = TextStyle(textDecoration = TextDecoration.LineThrough)
    var style by remember { mutableStateOf(regularTextStyle) }
    var bgColor by remember { mutableStateOf(Color.White) }
    var textAlignment by remember { mutableStateOf(TextAlign.Center) }
    var checkState by remember { mutableStateOf(activity.done.toString().toBoolean()) }
    var activityIcon by remember { mutableStateOf(R.drawable.ic_other_activities) }
    var contentColor by remember { mutableStateOf(Color.White) }

    when (activity.type) {
        stringResource(id = R.string.study) -> activityIcon = R.drawable.ic_study
        stringResource(id = R.string.sleep) -> activityIcon = R.drawable.ic_sleep
        stringResource(id = R.string.work) -> activityIcon = R.drawable.ic_work
        stringResource(id = R.string.exercise) -> activityIcon = R.drawable.ic_exercise
        stringResource(id = R.string.other) -> activityIcon - R.drawable.ic_other_activities
    }

    when (userType) {
        REGULAR_USER -> textAlignment = TextAlign.Center
        SUPERVISOR_USER -> textAlignment = TextAlign.Start
    }

    when (activity.createdBy) {
        REGULAR_USER -> bgColor = PerfectWhite
        SUPERVISOR_USER -> bgColor = GoldenYellow
    }

    when (checkState) {
        true -> {
            style = crossedTextStyle
            bgColor = Color.DarkGray
            contentColor = PerfectWhite
        }
        false -> {
            style = regularTextStyle
            contentColor = PerfectBlack
        }
    }
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(5.dp)
            .border(width = 1.dp, color = PerfectWhite, shape = RoundedCornerShape(12.dp)),
        color = bgColor
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = (painterResource(id = activityIcon)),
                    contentDescription = "activity icon",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .size(
                            IconSizes.medium
                        ),
                    tint = contentColor
                )
                Text(
                    text = activity.name.toString(),
                    color = contentColor,
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
                        colors = CheckboxDefaults.colors(
                            PerfectWhite,
                            uncheckedColor = PerfectBlack
                        )
                    )
                }
            }
        }
    }

}