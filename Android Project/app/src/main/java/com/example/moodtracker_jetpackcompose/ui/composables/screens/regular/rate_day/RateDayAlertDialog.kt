package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.rate_day.RateDayViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.GoldenYellow
import com.example.moodtracker_jetpackcompose.ui.theme.NavyBlue
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.google.firebase.auth.FirebaseAuth

private lateinit var rateDayViewModel: RateDayViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowRatingDialog(
    isDialogOpen: MutableState<Boolean>,
    navController: NavController,
    date: String
) {
    rateDayViewModel = RateDayViewModel()
    val firebaseUser = FirebaseAuth.getInstance().currentUser
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
                    .border(width = 1.dp, color = PerfectWhite, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.rate_your_day),
                        color = PerfectWhite,
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
                                painter = painterResource(id = R.drawable.ic_rating_star),
                                contentDescription = "star icon",
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
                                tint = if (i <= ratingState) GoldenYellow else Color(0xFFA2ADB1)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Button(
                        onClick = {
                            rateDayViewModel.saveRating(firebaseUser!!.uid, ratingState, date)
                            isDialogOpen.value = false
                            navController.navigate(Screen.RegularMainScreen.passDate(date = date))
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(PerfectWhite)
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit),
                            fontSize = 16.sp,
                            color = PerfectBlack
                        )
                    }
                }
            }

        }
    }
}
