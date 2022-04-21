package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import android.util.Log
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RegularUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserItem
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
    if(firebaseAuth.currentUser != null){
        SideEffect {
            supervisorMainViewModel.getSupervisedUsers {
                if(!it.isNullOrEmpty())
                    users = it
            }
        }
    }

    Scaffold(
        topBar = { SupervisorUserTopBar(navController = navController, title ="Supervised Users") },
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
                if(users.isNullOrEmpty()){
                    Text(
                        text = "You are not supervising any users!",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = Color.LightGray
                    )
                } else{
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
                                            contentDescription = "Delete",
                                            tint = Color.White,
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
                            Divider()
                        }
                    }
                }
            }
        })
}

@Composable
@Preview(showBackground = true)
fun SupervisorMainPreview() {
    SupervisorMainScreen(navController = rememberNavController())
}
