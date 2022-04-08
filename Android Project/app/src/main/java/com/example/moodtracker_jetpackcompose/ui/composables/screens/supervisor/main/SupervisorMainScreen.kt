package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RegularUserTopBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserItem
import com.google.firebase.auth.FirebaseAuth

private lateinit var supervisorMainViewModel: SupervisorMainViewModel
private lateinit var firebaseAuth: FirebaseAuth

var users = mutableListOf(
    RegularUser(username = "Pablo", email = "pablo911@gmail.com", userType = "Regular"),
    RegularUser(username = "Mat", email = "mat@gmail.com", userType = "Regular"),
    RegularUser(username = "Jake", email = "jake@gmail.com", userType = "Regular"),
    RegularUser(username = "Robert", email = "robert@gmail.com", userType = "Regular")
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SupervisorMainScreen(navController: NavHostController) {

    firebaseAuth = FirebaseAuth.getInstance()

    Scaffold(
        topBar = { RegularUserTopBar(navController = navController, title = "Users") },
        bottomBar = {
            SupervisorBottomBar(
                navController = navController
            )
        }, content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.9f)
                ) {
                    itemsIndexed(items = users,
                        key = { _, item ->
                            item.hashCode()
                        }
                    ) { index, item ->
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
                                UserItem(item)
                            },
                            directions = setOf(DismissDirection.EndToStart)
                        )
                        Divider()
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
