package com.example.better_me.ui.composables.screens.select_avatar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.better_me.ui.composables.reusable_components.AvatarSelector
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.White


/**
 * This function displays the alert dialog used to display the avatar selection window
 * @param isDialogOpen Mutable Boolean variable used to determine if the dialog window should be open
 * @param navController Navigation controller is used to navigate back to the correct screen
 */

@Composable
fun ShowSelectAvatarDialog(
    isDialogOpen: MutableState<Boolean>,
    userType: Int,
    navController: NavHostController
) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = White, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AvatarSelector(userType = userType, navController = navController)
                }
            }

        }
    }
}