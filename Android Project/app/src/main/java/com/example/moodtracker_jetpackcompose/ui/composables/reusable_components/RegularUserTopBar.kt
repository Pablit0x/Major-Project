package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components


import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()


@Composable
fun RegularUserTopBar(navController: NavController, title: String) {

    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(true) }

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
                onDismissRequest = { showMenu = false },
                modifier = Modifier.fillMaxWidth(0.55f)
            ) {

                DropdownMenuItem(onClick = {
                    Toast.makeText(context, "Dark Mode", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dark_mode),
                        contentDescription = "Dark mode",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = "Dark Mode",  modifier = Modifier.padding(end = 10.dp))
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it }
                    )
                }
                Divider(color = Color.LightGray, modifier = Modifier.fillMaxWidth(), thickness = 1.dp)

                DropdownMenuItem(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                    Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    firebaseAuth.signOut()
                }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = "Logout")
                }
            }


        }
    )
}


