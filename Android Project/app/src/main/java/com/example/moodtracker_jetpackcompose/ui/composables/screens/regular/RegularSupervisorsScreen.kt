package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular

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
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.AnimatedText
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RegularUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserItem
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.RegularMainViewModel
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.ShowAddSupervisorDialog
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

var isAddSupervisorDialogOpen: MutableState<Boolean> = mutableStateOf(false)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserSupervisorsScreen(navController: NavHostController) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val regularMainViewModel = RegularMainViewModel()
    var users: MutableList<SupervisorUser> by remember { mutableStateOf(mutableListOf()) }

    if (firebaseAuth.currentUser != null) {
        SideEffect {
            regularMainViewModel.getUserSupervisors { supervisors ->
                if (!supervisors.isNullOrEmpty()) {
                    users = supervisors
                    users.sortBy { it.username }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            RegularUserTopBar(
                navController = navController,
                title = "My Supervisors",
                showAddIcon = true,
                isHome = false
            )
        },
        bottomBar = {
            UserBottomBar(
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
                    AnimatedText(text = stringResource(id = R.string.user_not_supervised))
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
                                        regularMainViewModel.deleteSupervisor(item.uid!!)
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
                                    SupervisorUserItem(item)
                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                        }
                    }
                }
            }
        })
    ShowAddSupervisorDialog(
        isDialogOpen = isAddSupervisorDialogOpen
    )
}

fun setSupervisorDialog(showDialog: Boolean) {
    isAddSupervisorDialogOpen.value = showDialog
}