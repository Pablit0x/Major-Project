package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.requests.RequestsScreenViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor

@Composable
fun RequestItem(regularUser: RegularUser, myCallback: (RegularUser) -> Unit) {
    val requestsScreenViewModel = RequestsScreenViewModel()
    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(5.dp)
            .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(12.dp)),
        color = Color.White
    ){
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxHeight(0.3f)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(4f)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = regularUser.username.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxHeight()
                    )

                    Text(
                        text = regularUser.email.toString(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 12.sp,
                        style = TextStyle(fontWeight = FontWeight.Light),
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }
                IconButton(onClick = {
                    requestsScreenViewModel.acceptRequest(regularUser.uid!!)
                    myCallback(regularUser)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "",
                        tint = secondaryColor,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(48.dp)
                            .padding(end = 8.dp)
                    )
                }

                IconButton(onClick = {
                    requestsScreenViewModel.declineRequest(regularUser.uid!!)
                    myCallback(regularUser)
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "",
                        tint = primaryColor,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(48.dp)
                    )
                }
            } }
    }
}