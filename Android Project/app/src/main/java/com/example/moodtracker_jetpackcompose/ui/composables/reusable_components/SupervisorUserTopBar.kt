package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.screens.select_avatar.AvatarViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectGray
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()
private lateinit var avatarViewModel: AvatarViewModel

@Composable
fun SupervisorUserTopBar(navController: NavController, title: String, isHome: Boolean) {
    avatarViewModel = AvatarViewModel()
    var avatar by remember { mutableStateOf(R.drawable.ic_person) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center, color = PerfectWhite) },
        backgroundColor = PerfectGray,
        navigationIcon =
        {
            IconButton(onClick = { navController.navigateUp() }) {
                if (isHome && firebaseAuth.currentUser != null) {
                    SideEffect {
                        avatarViewModel.getAvatarID(
                            userID = firebaseAuth.currentUser!!.uid,
                            userType = SUPERVISOR_USER
                        ) {
                            avatar = if (it == -1) {
                                R.drawable.ic_person
                            } else {
                                avatarList[it]
                            }
                        }
                    }
                    AnimatedAvatarImage(imageResource = avatar)

                } else if (navController.previousBackStackEntry != null) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PerfectWhite
                    )
                }
            }
        },
        actions = {

            IconButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                    Toast.makeText(
                        context,
                        context.getString(R.string.logged_out_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                    firebaseAuth.signOut()
                },
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
            ) {
                Icon(Icons.Default.ExitToApp, "Logout", tint = PerfectWhite)
            }
        }
    )
}