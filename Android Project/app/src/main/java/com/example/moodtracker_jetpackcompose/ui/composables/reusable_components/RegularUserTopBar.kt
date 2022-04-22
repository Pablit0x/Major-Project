package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.setSupervisorDialog
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()


@Composable
fun RegularUserTopBar(navController: NavController, title: String) {

    val isAddSupervisorDialogOpen by remember{ mutableStateOf(false)}
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center) },
        backgroundColor = Color(0xFF191919),
        actions = {

            OutlinedButton(onClick = { setSupervisorDialog(!isAddSupervisorDialogOpen)},
                modifier = Modifier
                    .padding(end = 8.dp)
                    .width(45.dp)
                    .height(45.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.DarkGray, backgroundColor = Color.LightGray)
            ) {
                Icon(painterResource(id = R.drawable.ic_add_person), contentDescription = "Add Supervisor")
            }

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


