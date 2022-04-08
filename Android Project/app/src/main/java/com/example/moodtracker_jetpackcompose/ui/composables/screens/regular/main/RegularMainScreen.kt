package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.*
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val regularMainViewModel = RegularMainViewModel()
val firebaseAuth = FirebaseAuth.getInstance()
var isAddActivityDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isAddSupervisorDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFinishDayDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var userRating: Int? = null
var date: String = ""


@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(
    ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RegularMainScreen(navController: NavHostController, selectedDate: String) {

    var activities: MutableList<Activity> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate =
            "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        date = selectedDate.ifEmpty {
            formattedDate
        }
        firebaseAuth.currentUser?.let { it ->
            regularMainViewModel.readData(date, it.uid) {
                if (!it.isNullOrEmpty()) {
                    activities = it
                }
            }
            regularMainViewModel.readRating(date, it.uid) {
                userRating = it
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
                FloatingActionButton(
                    onClick = { isAddActivityDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = Color.White,
                ) {
                    Icon(imageVector = Icons.Default.Add, "Add activity")
                }

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                ExtendedFloatingActionButton(
                    text = { Text(text = "Rate the day") },
                    icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "") },
                    onClick = { isFinishDayDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = Color.White,
                )
            }

        },
        topBar = { RegularUserTopBar(navController, date) },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(padding)
            ) {
                if (userRating != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f)
                            .background(color = secondaryColor)
                    ) {
                        for (i in 1..userRating!!) {
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
                                        regularMainViewModel.deleteActivity(
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
                                        DismissDirection.EndToStart -> Color.Red
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
                                    ListItem(item, date = date)
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                            Divider()

                            if (index == activities.size - 1) {
                                Spacer(modifier = Modifier.padding(bottom = 70.dp))
                            }
                        }
                    }
                }
                ShowAddActivityAlertDialog(
                    isDialogOpen = isAddActivityDialogOpen,
                    navController = navController,
                    date = date
                )
                ShowRatingDialog(
                    isDialogOpen = isFinishDayDialogOpen,
                    navController = navController,
                    date = date
                )
                ShowAddSupervisorDialog(isDialogOpen = isAddSupervisorDialogOpen)
            }
        })
}

fun resetUserRating() {
    userRating = null
}

fun setSupervisorDialog(showDialog : Boolean){
    isAddSupervisorDialogOpen.value = showDialog
}