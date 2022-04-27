package com.example.better_me.ui.composables.reusable_components


import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.better_me.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.better_me.Screen
import com.example.better_me.data.model.Constants.REGULAR_USER
import com.example.better_me.ui.composables.screens.regular.main.currentUser
import com.example.better_me.ui.composables.screens.regular.setSupervisorDialog
import com.example.better_me.ui.composables.screens.select_avatar.AvatarViewModel
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import com.google.firebase.auth.FirebaseAuth

private val firebaseAuth = FirebaseAuth.getInstance()
private lateinit var avatarViewModel: AvatarViewModel

@Composable
fun RegularUserTopBar(
    navController: NavController,
    title: String,
    showAddIcon: Boolean,
    isHome: Boolean
) {
    avatarViewModel = AvatarViewModel()
    val isAddSupervisorDialogOpen by remember { mutableStateOf(false) }
    var avatar by remember { mutableStateOf(R.drawable.ic_person) }
    val context = LocalContext.current

    TopAppBar(
        title = { Text(title, textAlign = TextAlign.Center) },
        backgroundColor = PerfectGray,
        navigationIcon =
        {
            IconButton(onClick = { navController.navigateUp() }) {
                if (isHome) {
                    SideEffect {
                        if (currentUser != null) {
                            avatarViewModel.getAvatarID(
                                userID = currentUser.uid,
                                userType = REGULAR_USER
                            ) {
                                avatar = if (it == -1) {
                                    R.drawable.ic_person
                                } else {
                                    avatarList[it]
                                }
                            }
                        }
                    }
                    AnimatedAvatarImage(imageResource = avatar)

                } else if (navController.previousBackStackEntry != null) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }
        },
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
                        tint = White
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
                Icon(Icons.Default.ExitToApp, "logout icon", tint = White)
            }
        }
    )
}


