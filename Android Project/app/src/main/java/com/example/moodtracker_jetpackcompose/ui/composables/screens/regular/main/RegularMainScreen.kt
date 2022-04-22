package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.*
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.*
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val regularMainViewModel = RegularMainViewModel()
val firebaseAuth = FirebaseAuth.getInstance()
val currentUser = firebaseAuth.currentUser
var isAddActivityDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isAddSupervisorDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFinishDayDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFeedbackDialogOpen: MutableState<Boolean> = mutableStateOf(false)


@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(
    ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RegularMainScreen(navController: NavHostController, selectedDate: String) {
    var feedbackComment by remember { mutableStateOf("") }
    var userRating by remember { mutableStateOf(0) }
    var date by remember { mutableStateOf("") }
    var activities: MutableList<Activity> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate =
            "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        date = selectedDate.ifEmpty {
            formattedDate
        }
        currentUser?.let { it ->
            getAllActivities(date, it.uid) {
                if (!it.isNullOrEmpty()) {
                    activities = it
                }
            }
            getRating(date, it.uid) {
                userRating = it
            }
            getFeedback(date = date, uid = it.uid) {
                feedbackComment = it
            }
        }
    }
    Scaffold(
        bottomBar = {
            UserBottomBar(navController = navController)
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                if (feedbackComment.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = { isFeedbackDialogOpen.value = !isFeedbackDialogOpen.value },
                        backgroundColor = primaryColor,
                        contentColor = Color.White,
                    ) {
                        Icon(imageVector = Icons.Default.Warning, "Add activity")
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                }

                FloatingActionButton(
                    onClick = { isAddActivityDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = Color.White,
                ) {
                    Icon(imageVector = Icons.Default.Add, "Add activity")
                }

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                ExtendedFloatingActionButton(
                    text = { Text(text = "Rate the day", fontFamily = FontFamily.Monospace) },
                    icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "") },
                    onClick = { isFinishDayDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = Color.White,
                )
            }

        },
        topBar = { RegularUserTopBar(navController, "My Activities") },
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
                        color = Color.White,
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
                                    painter = painterResource(id = R.drawable.ic_round_star_rate_24),
                                    contentDescription = "star",
                                    modifier = Modifier
                                        .width(52.dp)
                                        .height(52.dp),
                                    tint = Color(0xFFFFD700)
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
                            val state = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        activities.remove(item)
                                        deleteActivity(
                                            activity = item,
                                            uid = firebaseAuth.uid!!,
                                            date = date
                                        )

                                    }
                                    true
                                }
                            )

                            SwipeToDismiss(
                                state = state,
                                background = {
                                    val color = when (state.dismissDirection) {
                                        DismissDirection.StartToEnd -> Color.Transparent
                                        DismissDirection.EndToStart -> primaryColor
                                        null -> Color.Transparent
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color = color)
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.White,
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }
                                },
                                dismissContent = {
                                    ListItem(
                                        activity = item,
                                        date = date,
                                        userType = REGULAR_USER
                                    )
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                            if (index == activities.size) {
                                Spacer(modifier = Modifier.padding(bottom = 70.dp))
                            }
                        }
                    }
                }

                if (isFeedbackDialogOpen.value) {
                    Dialog(onDismissRequest = { isFeedbackDialogOpen.value = false }) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            color = Color((0xFF2D4263))
                        ) {
                            Column(
                                modifier = Modifier.padding(5.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.padding(5.dp))

                                Text(
                                    text = "Feedback",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                TextField(
                                    value = feedbackComment,
                                    onValueChange = { text ->
                                        feedbackComment = text
                                    },
                                    enabled = false,
                                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                                    colors = textFieldColors(
                                        backgroundColor = Color((0xFF2D4263)),
                                        disabledTextColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent
                                    ),
                                )

                                Spacer(modifier = Modifier.padding(5.dp))


                                Button(
                                    onClick = {
                                        isFeedbackDialogOpen.value = false
                                    },
                                    modifier = Modifier
                                        .height(70.dp)
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    shape = RoundedCornerShape(5.dp),
                                    colors = ButtonDefaults.buttonColors(Color.White)
                                ) {
                                    Text(text = "Close", fontSize = 16.sp, color = Color.Black)
                                }
                            }
                        }
                    }
                }

                ShowAddActivityAlertDialog(
                    isDialogOpen = isAddActivityDialogOpen,
                    navController = navController,
                    date = date,
                    userType = REGULAR_USER,
                    userID = currentUser!!.uid,
                    username = ""
                )
                ShowRatingDialog(
                    isDialogOpen = isFinishDayDialogOpen,
                    navController = navController,
                    date = date
                )
                ShowAddSupervisorDialog(
                    isDialogOpen = isAddSupervisorDialogOpen
                )
            }
        })
}

fun setSupervisorDialog(showDialog: Boolean) {
    isAddSupervisorDialogOpen.value = showDialog
}