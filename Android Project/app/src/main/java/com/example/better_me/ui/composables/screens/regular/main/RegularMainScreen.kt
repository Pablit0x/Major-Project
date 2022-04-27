package com.example.better_me.ui.composables.screens.regular.main

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.better_me.data.model.Activity
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.ui.composables.reusable_components.ActivityItem
import com.example.better_me.ui.composables.reusable_components.AnimatedText
import com.example.better_me.ui.composables.reusable_components.RegularUserTopBar
import com.example.better_me.ui.composables.reusable_components.UserBottomBar
import com.example.better_me.ui.composables.screens.regular.add_activity.ShowAddActivityAlertDialog
import com.example.better_me.ui.composables.screens.regular.rate_day.ShowRatingDialog
import com.example.better_me.ui.composables.screens.select_avatar.ShowSelectAvatarDialog
import com.example.better_me.ui.composables.screens.supervisor.main.isSelectAvatarDialogOpen
import com.example.better_me.ui.theme.*
import com.example.better_me.R
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val firebaseAuth = FirebaseAuth.getInstance()
val currentUser = firebaseAuth.currentUser
var isAddActivityDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFinishDayDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFeedbackDialogOpen: MutableState<Boolean> = mutableStateOf(false)
lateinit var regularMainViewModel: RegularMainViewModel

@OptIn(
    ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun RegularMainScreen(navController: NavHostController, selectedDate: String) {
    regularMainViewModel = RegularMainViewModel()
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
            regularMainViewModel.getAllActivities(date, it.uid) {
                if (!it.isNullOrEmpty()) {
                    activities = it
                }
            }
            regularMainViewModel.getRating(date, it.uid) {
                userRating = it
            }
            regularMainViewModel.getFeedback(date = date, uid = it.uid) {
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
                        contentColor = White,
                    ) {
                        Icon(imageVector = Icons.Default.Warning, "feedback icon")
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                }

                FloatingActionButton(
                    onClick = { isAddActivityDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = White,
                ) {
                    Icon(imageVector = Icons.Default.Add, "add activity icon")
                }

                Spacer(modifier = Modifier.padding(bottom = 10.dp))

                ExtendedFloatingActionButton(
                    text = { Text(text = "Rate the day", fontFamily = FontFamily.Monospace) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "rate the day icon"
                        )
                    },
                    onClick = { isFinishDayDialogOpen.value = true },
                    backgroundColor = secondaryColor,
                    contentColor = White,
                )
            }

        },
        topBar = {
            RegularUserTopBar(
                navController,
                stringResource(id = R.string.my_activities),
                showAddIcon = false,
                isHome = true
            )
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
                        color = White,
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
                                            contentDescription = "delete icon",
                                            tint = White,
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }
                                },
                                dismissContent = {
                                    ActivityItem(
                                        activity = item,
                                        date = date,
                                        userType = REGULAR_USER
                                    )
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                            if (index == activities.size - 1) {
                                Spacer(modifier = Modifier.padding(bottom = 200.dp))
                            }
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        AnimatedText(stringResource(id = R.string.empty_activity_list))
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
                                    color = White,
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
                                    text = stringResource(id = R.string.feedback),
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = White
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
                                        backgroundColor = NavyBlue,
                                        disabledTextColor = White,
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
                                    colors = ButtonDefaults.buttonColors(White)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.close),
                                        fontSize = 16.sp,
                                        color = Black
                                    )
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
                ShowSelectAvatarDialog(
                    isDialogOpen = isSelectAvatarDialogOpen,
                    userType = REGULAR_USER,
                    navController = navController
                )
            }
        })
}