package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.RegularUser

@Composable
fun UserItem(user: RegularUser, navController: NavHostController) {

    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxHeight(0.3f)
                .defaultMinSize(minHeight = 80.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .size(48.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(4f)
            ) {
                Text(
                    text = user.username.toString(),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxHeight()
                )

                Text(
                    text = user.email.toString(),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    style = TextStyle(color = Color.Gray),
                    modifier = Modifier
                        .fillMaxHeight()
                )
            }
            IconButton(onClick = {
                navController.navigate(Screen.SupervisorCalendarScreen.passUsernameAndUID(username = user.username!!,user.uid!!))
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow Forward",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .size(48.dp)
                )
            }
        }
        Divider(color = Color.LightGray, modifier = Modifier.weight(1f), thickness = 1.dp)
    }
}
