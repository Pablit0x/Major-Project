package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.*
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.add_activity.ShowAlertDialog
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDateTime


val regularMainViewModel = RegularMainViewModel()
val firebaseAuth = FirebaseAuth.getInstance()
var isDialogOpen: MutableState<Boolean> = mutableStateOf(false)


@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegularMainScreen(navController: NavHostController) {
    var activities: MutableList<Activity> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        val currentDate = LocalDateTime.now()
        val formattedDate = "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
        regularMainViewModel.readData(formattedDate, firebaseAuth.currentUser!!.uid) {
            if (!it.isNullOrEmpty()) {
                activities = it
            }
        }
    }
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
        topBar = { RegularUserTopBar(navController, "Today's Activities") },
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (activities.isNotEmpty()) {
                    LazyColumn {
                        items(activities) { item ->
                            ListItem(activity = item)
                        }
                    }
                }

                ShowAlertDialog(isDialogOpen = isDialogOpen, navController = navController)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = { isDialogOpen.value = true },
                    backgroundColor = primaryColor
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "")
                }
            }
        })
}