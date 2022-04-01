package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.ShowAddSupervisorDialog
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.setSupervisorDialog
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()


@SuppressLint("UnrememberedMutableState")
@Composable
fun RegularUserTopBar(navController: NavController, title: String) {

    var showMenu by remember { mutableStateOf(false) }
    var isAddSupervisorDialogOpen by remember{ mutableStateOf(false)}
    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(true) }

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center) },
        backgroundColor = Color.White,
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

//            DropdownMenu(
//                expanded = showMenu,
//                onDismissRequest = { showMenu = false },
//                modifier = Modifier.fillMaxWidth(0.55f)
//            ) {
//
//                DropdownMenuItem(onClick = {
//                    Toast.makeText(context, "Dark Mode", Toast.LENGTH_SHORT).show()
//                }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_dark_mode),
//                        contentDescription = "Dark mode",
//                        modifier = Modifier.padding(end = 10.dp)
//                    )
//                    Text(text = "Dark Mode",  modifier = Modifier.padding(end = 10.dp))
//                    Switch(
//                        checked = checkedState.value,
//                        onCheckedChange = { checkedState.value = it }
//                    )
//                }
//                Divider(color = Color.LightGray, modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
//
//                DropdownMenuItem(onClick = {
//                    navController.navigate(Screen.LoginScreen.route)
//                    Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
//                    firebaseAuth.signOut()
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.ExitToApp,
//                        contentDescription = "Logout",
//                        modifier = Modifier.padding(end = 10.dp)
//                    )
//                    Text(text = "Logout")
//                }
//            }


        }
    )
}


