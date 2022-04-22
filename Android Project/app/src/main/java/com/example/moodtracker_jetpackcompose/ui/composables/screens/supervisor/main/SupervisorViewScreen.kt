package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.data.model.getFeedback
import com.example.moodtracker_jetpackcompose.data.model.getPublicActivities
import com.example.moodtracker_jetpackcompose.data.model.getRating
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.ActivityItem
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.FeedbackBox
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.ShowAddActivityAlertDialog
import com.example.moodtracker_jetpackcompose.ui.theme.GoldenYellow
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val firebaseAuthentication = FirebaseAuth.getInstance()
var isAddActivityDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isAddFeedbackDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var feedbackComment: MutableState<String> = mutableStateOf("")
var date: String = ""


@OptIn(
    ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun SupervisorViewScreen(
    navController: NavHostController,
    selectedDate: String,
    userUID: String,
    username: String
) {

    var userRating by remember { mutableStateOf(0) }
    var activities: MutableList<Activity> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate =
            "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        date = selectedDate.ifEmpty {
            formattedDate
        }
        getPublicActivities(date, uid = userUID) {
            if (!it.isNullOrEmpty()) {
                activities = it
            }
        }
        getRating(date, uid = userUID) {
            userRating = it
        }
        getFeedback(date = date, uid = userUID) {
            feedbackComment = mutableStateOf(it)
        }
    }
    Scaffold(
        bottomBar = {
            SupervisorBottomBar(navController = navController)
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {

                FloatingActionButton(
                    onClick = { isAddActivityDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = PerfectWhite,
                ) {
                    Icon(imageVector = Icons.Default.Add, "add activity icon")
                }

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = stringResource(id = R.string.leave_feedback),
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_feedback),
                            contentDescription = "add feedback icon"
                        )
                    },
                    onClick = { isAddFeedbackDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = PerfectWhite,
                )
            }

        },
        topBar = {
            SupervisorUserTopBar(navController = navController, title = "$username Activities")
        },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(padding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(color = Color(0xFF2D4263))
                ) {
                    Text(
                        text = date,
                        color = PerfectWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (userRating != 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            for (i in 1..userRating) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_rating_star),
                                    contentDescription = "star icon",
                                    modifier = Modifier
                                        .width(52.dp)
                                        .height(52.dp),
                                    tint = GoldenYellow
                                )
                            }
                        }
                    }
                }
                if (activities.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.9f)
                    ) {
                        itemsIndexed(items = activities,
                            key = { _, item ->
                                item.hashCode()
                            }
                        ) { index, item ->
                            ActivityItem(activity = item, date = date, userType = SUPERVISOR_USER)
//                            val state = rememberDismissState(
//                                confirmStateChange = {
//                                    if (it == DismissValue.DismissedToStart) {
//                                        activities.remove(item)
//                                        deleteActivity(
//                                            activity = item,
//                                            uid = firebaseAuthentication.uid!!,
//                                            date = date
//                                        )
//                                    }
//                                    true
//                                }
//                            )
//
//                            SwipeToDismiss(
//                                state = state,
//                                background = {
//                                    val color = when (state.dismissDirection) {
//                                        DismissDirection.StartToEnd -> Color.Transparent
//                                        DismissDirection.EndToStart -> Color.Red
//                                        null -> Color.Transparent
//                                    }
//
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .background(color = color)
//                                            .padding(8.dp)
//                                    ) {
//                                        Icon(
//                                            imageVector = Icons.Default.Delete,
//                                            contentDescription = "Delete",
//                                            tint = Color.White,
//                                            modifier = Modifier.align(Alignment.CenterEnd)
//                                        )
//                                    }
//                                },
//                                dismissContent = {
//                                    ListItem(
//                                        activity = item,
//                                        date = date,
//                                        userType = SUPERVISOR_USER
//                                    )
//                                },
//                                directions = setOf(DismissDirection.EndToStart)
//                            )
//                            if (index == activities.size - 1) {
//                                Spacer(modifier = Modifier.padding(bottom = 150.dp))
//                            }
//                        }
                        }
                    }
                }

                ShowAddActivityAlertDialog(
                    isDialogOpen = isAddActivityDialogOpen,
                    navController = navController,
                    date = date,
                    userType = SUPERVISOR_USER,
                    userID = userUID,
                    username = username
                )
                FeedbackBox(
                    isDialogOpen = isAddFeedbackDialogOpen,
                    date = date,
                    userID = userUID,
                    feedbackComment = feedbackComment
                )
            }
        })
}

