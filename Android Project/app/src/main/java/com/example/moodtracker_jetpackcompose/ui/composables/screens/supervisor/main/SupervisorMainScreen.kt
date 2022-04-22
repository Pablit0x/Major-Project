package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.AnimatedText
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserItem
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

private lateinit var supervisorMainViewModel: SupervisorMainViewModel
private lateinit var firebaseAuth: FirebaseAuth


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SupervisorMainScreen(navController: NavHostController) {

    var users: MutableList<RegularUser?> by remember { mutableStateOf(mutableListOf()) }

    firebaseAuth = FirebaseAuth.getInstance()
    supervisorMainViewModel = SupervisorMainViewModel()
    if (firebaseAuth.currentUser != null) {
        SideEffect {
            supervisorMainViewModel.getSupervisedUsers { supervisedUsers ->
                if (!supervisedUsers.isNullOrEmpty()) {
                    users = supervisedUsers
                    users.sortBy { it!!.username }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SupervisorUserTopBar(
                navController = navController,
                title = "Supervised Users"
            )
        },
        bottomBar = {
            SupervisorBottomBar(
                navController = navController
            )
        }, content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (users.isNullOrEmpty()) {
                    AnimatedText(text = stringResource(id = R.string.no_supervised_users))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(0.9f)
                    ) {
                        itemsIndexed(items = users,
                            key = { _, item ->
                                item.hashCode()
                            }
                        ) { _, item ->
                            val state = rememberDismissState(
                                confirmStateChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        users.remove(item)
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
                                            tint = PerfectWhite,
                                            modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }
                                },
                                dismissContent = {
                                    if (item != null) {
                                        UserItem(item, navController = navController)
                                    }
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
        })
}
