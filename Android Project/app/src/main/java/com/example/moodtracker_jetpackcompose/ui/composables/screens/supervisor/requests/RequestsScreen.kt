package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.requests

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.RequestItem
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorBottomBar
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.SupervisorUserTopBar
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import kotlinx.coroutines.delay

private lateinit var requestsScreenViewModel: RequestsScreenViewModel

@Composable
fun RequestsScreen(navController: NavHostController) {
    requestsScreenViewModel = RequestsScreenViewModel()
    var users: MutableList<RegularUser> by remember { mutableStateOf(mutableListOf()) }

    SideEffect {
        requestsScreenViewModel.getAllRequests {
            users = it
        }
    }

    Scaffold(topBar = {
        SupervisorUserTopBar(navController = navController, title = "Requests")
    },
        bottomBar = { SupervisorBottomBar(navController = navController) }, content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (users.isEmpty()) {
                    Text(
                        text = "You have no pending requests!",
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        color = Color.LightGray
                    )
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
                            RequestItem(regularUser = item) {
                                users.remove(it)
                            }
                            Divider()
                        }
                    }
                }
            }
        })
}