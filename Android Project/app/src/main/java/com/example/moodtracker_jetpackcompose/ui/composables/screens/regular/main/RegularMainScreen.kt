package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.add_activity.ShowAlertDialog
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val regularMainViewModel = RegularMainViewModel()
val firebaseAuth = FirebaseAuth.getInstance()
var isDialogOpen: MutableState<Boolean> = mutableStateOf(false)
var isFinishDayDialogOpen : MutableState<Boolean> = mutableStateOf(false)
var userRating : Int? = null


@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun RegularMainScreen(navController: NavHostController) {
    var activities: MutableList<Activity> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate =
            "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        firebaseAuth.currentUser?.let { it ->
            regularMainViewModel.readData(formattedDate, it.uid) {
                if (!it.isNullOrEmpty()) {
                    activities = it
                }
            }
        }
    }
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController) },
        floatingActionButton = {
            Column() {
                FloatingActionButton(
                    onClick = { isDialogOpen.value = true },
                    backgroundColor = primaryColor,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, "Add activity")
                }
                Spacer(modifier = Modifier.padding(2.dp))
                FloatingActionButton(
                    onClick = { isFinishDayDialogOpen.value = true },
                    backgroundColor = primaryColor,
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_flag_24),
                        "Finish day"
                    )
                }
            }

        },
        topBar = { RegularUserTopBar(navController, "Today's Activities") },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(padding)
            ) {
                if(userRating != null){

                }
                if (activities.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.9f)
                    ) {
                        activities.removeIf { obj: Activity -> obj == null }
                        itemsIndexed(activities) { index, item->
                            ListItem(activity = item)
                            if(index == activities.size - 1){
                                Spacer(modifier = Modifier.padding(70.dp))
                            }
                        }
                    }
                }
                ShowAlertDialog(isDialogOpen = isDialogOpen, navController = navController)
                ShowRatingDialog(isDialogOpen = isFinishDayDialogOpen, navController = navController)
            }
        })
}