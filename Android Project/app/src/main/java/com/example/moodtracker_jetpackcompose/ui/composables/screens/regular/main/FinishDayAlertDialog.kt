package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtracker_jetpackcompose.R
import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.saveRating
import com.example.moodtracker_jetpackcompose.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

private lateinit var mRegularMainViewModel: RegularMainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowRatingDialog(isDialogOpen: MutableState<Boolean>, navController: NavController, date : String) {

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    mRegularMainViewModel = RegularMainViewModel()
    var ratingState by remember { mutableStateOf(3) }
    var selected by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (selected) 58.dp else 52.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )


    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
                color = Color((0xFF2D4263))
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = "Rate your day",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        for (i in 1..5) {
                            Icon(
                                painter = painterResource(id =R.drawable.ic_round_star_rate_24),
                                contentDescription = "star",
                                modifier = Modifier
                                    .width(size)
                                    .height(size)
                                    .pointerInteropFilter {
                                        when (it.action) {
                                            MotionEvent.ACTION_DOWN -> {
                                                selected = true
                                                ratingState = i
                                            }
                                            MotionEvent.ACTION_UP -> {
                                                selected = false
                                            }
                                        }
                                        true
                                    },
                                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Button(
                        onClick = {
                            saveRating(firebaseUser!!.uid, ratingState, date)
                            isDialogOpen.value = false
                            navController.navigate(Screen.RegularMainScreen.passDate(date = date))
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ){
                        Text(text = "Submit", fontSize = 16.sp, color = Color.Black)
                    }
                }
            }

        }
    }
}
