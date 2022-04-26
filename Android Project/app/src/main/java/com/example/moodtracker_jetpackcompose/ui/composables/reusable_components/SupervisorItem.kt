package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.RegularUser
import com.example.moodtracker_jetpackcompose.data.model.SupervisorUser
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.currentUser
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun SupervisorUserItem(user: SupervisorUser) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .padding(5.dp)
            .border(width = 1.dp, color = PerfectWhite, shape = RoundedCornerShape(12.dp)),
        color = PerfectWhite
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .defaultMinSize(minHeight = 80.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person icon",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .size(48.dp),
                    tint = PerfectBlack
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(4f)
                ) {
                    Text(
                        text = user.username.toString(),
                        textAlign = TextAlign.Start,
                        color = PerfectBlack,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxHeight()
                    )

                    Text(
                        text = user.email.toString(),
                        textAlign = TextAlign.Start,
                        color = PerfectBlack,
                        fontSize = 12.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Light
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                    )
                }
                IconButton(onClick = {
                    val uri = Uri.parse("mailto:${user.email}")
                        .buildUpon()
                        .build()
                    val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
                    if(emailIntent.resolveActivity(context.packageManager) != null){
                        startActivity(context ,emailIntent,  null)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email icon",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .size(36.dp)
                    )
                }
            }
        }
    }

}