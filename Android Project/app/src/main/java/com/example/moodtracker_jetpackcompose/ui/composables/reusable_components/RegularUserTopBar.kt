package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components


import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.setSupervisorDialog
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectGray
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()


@Composable
fun RegularUserTopBar(navController: NavController, title: String, showAddIcon: Boolean) {

    val isAddSupervisorDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center) },
        backgroundColor = PerfectGray,
        actions = {
            if (showAddIcon) {
                IconButton(
                    onClick = { setSupervisorDialog(!isAddSupervisorDialogOpen) },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(45.dp)
                        .height(45.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_add_person),
                        contentDescription = "add supervisor icon",
                        tint = PerfectWhite
                    )
                }
            }

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
                Icon(Icons.Default.ExitToApp, "logout icon", tint = PerfectWhite)
            }
        }
    )
}


