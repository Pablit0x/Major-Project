package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.RegularUser

@Composable
fun UserItem(user: RegularUser) {

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
            Icon(
                painter = painterResource(id = R.drawable.ic_person), contentDescription = "", modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Text(
                text = user.username.toString(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(4f)
                    .fillMaxHeight()
                    .align(CenterVertically)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "Arrow Forward",
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
            }
        }
        Divider(color = Color.LightGray, modifier = Modifier.weight(1f), thickness = 1.dp)
    }
}

@Composable
@Preview
fun prev(){
    UserItem(user = RegularUser(username = "Pablo991", email = "pablo911@gmail.com", userType = "Regular"))
}
