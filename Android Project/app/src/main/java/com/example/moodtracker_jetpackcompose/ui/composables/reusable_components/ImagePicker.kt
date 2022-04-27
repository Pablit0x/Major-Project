package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.data.model.setAvatar
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.setAvatarDialog
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite

val avatarList: List<Int> = listOf(
    R.drawable.ic_avatar1,
    R.drawable.ic_avatar2,
    R.drawable.ic_avatar3,
    R.drawable.ic_avatar4,
    R.drawable.ic_avatar5,
    R.drawable.ic_avatar6,
    R.drawable.ic_avatar7,
    R.drawable.ic_avatar8,
)

@Composable
fun ImagePicker(userType: Int, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.select_avatar),
            color = PerfectWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in 0..3) {
                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(55.dp),
                    shape = CircleShape,
                    elevation = 2.dp
                ) {
                    val image: Painter = painterResource(id = avatarList[index])
                    Image(painter = image, contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(55.dp)
                            .clickable {
                                setAvatar(userType = userType, avatarID = index)
                                when (userType) {
                                    REGULAR_USER -> navController.navigate(
                                        Screen.RegularMainScreen.route
                                    )
                                    SUPERVISOR_USER -> navController.navigate(
                                        Screen.SupervisorMainScreen.route
                                    )
                                }
                                setAvatarDialog(avatarDialogState = false)
                            })
                }
            }
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (index in 4..7) {
                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .size(55.dp),
                    shape = CircleShape,
                    elevation = 2.dp
                ) {
                    val image: Painter = painterResource(id = avatarList[index])
                    Image(painter = image, contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(55.dp)
                            .clickable {
                                setAvatar(userType = userType, avatarID = index)
                                when (userType) {
                                    REGULAR_USER -> navController.navigate(
                                        Screen.RegularMainScreen.route
                                    )
                                    SUPERVISOR_USER -> navController.navigate(
                                        Screen.SupervisorMainScreen.route
                                    )
                                }
                                setAvatarDialog(avatarDialogState = false)
                            })
                }
            }
        }
    }
}