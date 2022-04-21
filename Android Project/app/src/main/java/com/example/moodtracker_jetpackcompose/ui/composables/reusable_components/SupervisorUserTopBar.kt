package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.setSupervisorDialog
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()

@Composable
fun SupervisorUserTopBar(navController: NavController, title: String) {

    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center) },
        backgroundColor = Color.White,
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        },
        actions = {
            OutlinedButton(onClick = {
                navController.navigate(Screen.LoginScreen.route)
                Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
            },
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.DarkGray, backgroundColor = Color.LightGray)
            ) {
                Icon(Icons.Default.ExitToApp, "Logout")
            }
        }
    )
}