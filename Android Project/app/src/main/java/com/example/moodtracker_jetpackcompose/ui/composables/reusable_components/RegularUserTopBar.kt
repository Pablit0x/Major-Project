package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components


import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()


@Composable
fun RegularUserTopBar(navController: NavController, title: String) {

    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title) },
        actions = {

            IconButton(onClick = {
                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
            }) {
                Icon(painterResource(id = R.drawable.ic_add_person), "Add supervisor")
            }

            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.MoreVert, "Menu")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {

                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Settings")
                }

                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    firebaseAuth.signOut()
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Text(text = "Logout")
                }

            }


        }
    )


}

