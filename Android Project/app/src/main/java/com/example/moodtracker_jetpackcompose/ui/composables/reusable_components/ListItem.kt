package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

@Composable
fun ListItem(activity : Activity){
    val regularTextStyle = TextStyle(textDecoration = TextDecoration.None)
    val crossedTextStyle = TextStyle(textDecoration = TextDecoration.LineThrough)
    var style by remember { mutableStateOf(regularTextStyle)}
    var bgColor by remember { mutableStateOf(Color.White)}
    var dividerColor by remember { mutableStateOf(Color.LightGray)}
    var checkState by remember { mutableStateOf(activity.done.toString().toBoolean())}


    when(checkState){
        true -> {
            style = crossedTextStyle
            bgColor = Color.LightGray
            dividerColor = Color.White
        }
        false -> {
            style = regularTextStyle
            bgColor = Color.White
            dividerColor = Color.LightGray
        }
    }

    Column(){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .background(color = bgColor)
                .padding(4.dp)
                .height(80.dp)
                .fillMaxWidth()
        ){
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "")
            Text(text = activity.name.toString(), style = style ,fontFamily = FontFamily.Monospace, fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
            Checkbox(checked = checkState, onCheckedChange = {
                checkState = it
                setDone(!activity.done.toString().toBoolean(), activity)}, modifier = Modifier
                .scale(1.5f)
                .padding(end = 8.dp),
            colors = CheckboxDefaults.colors(secondaryColor))
        }
        Divider(color = dividerColor, modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
    }
}

fun setDone(done : Boolean, activity: Activity){
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val activityID = activity.id as String
    val db = Firebase.firestore
    val updatedActivity = Activity(activity.name, activity.type, done, activity.id)
    val currentDate = LocalDateTime.now()
    val formattedDate = "${currentDate.dayOfMonth}-${currentDate.monthValue}-${currentDate.year}"
    db.collection("records").document(uid).collection(formattedDate).document(activityID).set(updatedActivity)
}