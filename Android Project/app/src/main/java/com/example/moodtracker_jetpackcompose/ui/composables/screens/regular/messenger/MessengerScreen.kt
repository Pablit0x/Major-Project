package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.messenger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.BottomBar

@Composable
fun MessengerScreen(navHostController: NavHostController) {
    Scaffold(bottomBar = { BottomBar(navController = navHostController) }, content = { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(text = "Messenger", fontSize = 24.sp)
        }

    })
}