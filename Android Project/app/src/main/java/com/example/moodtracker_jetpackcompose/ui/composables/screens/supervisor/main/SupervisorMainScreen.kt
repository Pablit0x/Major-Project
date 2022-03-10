package com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

private lateinit var supervisorMainViewModel: SupervisorMainViewModel
private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun SupervisorMainScreen(navController: NavController) {
    firebaseAuth = FirebaseAuth.getInstance()
    val firebaseEmail = firebaseAuth.currentUser!!.email
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Supervisor user: $firebaseEmail", fontSize = 16.sp)
    }
}

@Composable
@Preview(showBackground = true)
fun SupervisorMainPreview() {
    SupervisorMainScreen(navController = rememberNavController())
}
