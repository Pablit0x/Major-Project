package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.BottomBar
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

private lateinit var regularMainViewModel: RegularMainViewModel
private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun RegularMainScreen(navController: NavHostController) {
    regularMainViewModel = RegularMainViewModel()
    firebaseAuth = FirebaseAuth.getInstance()
    val firebaseEmail = firebaseAuth.currentUser!!.email
    Scaffold(bottomBar = { BottomBar(navController = navController) }, content = { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (firebaseEmail != null) {
                Text(text = firebaseEmail, fontSize = 24.sp)
            }
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
                onClick = { /*TODO*/ },
                backgroundColor = primaryColor
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    })
}