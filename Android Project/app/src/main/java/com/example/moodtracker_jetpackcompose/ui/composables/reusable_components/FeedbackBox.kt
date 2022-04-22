package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import android.view.MotionEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.data.model.addFeedback
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.SupervisorMainViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.tertiaryColor

@Composable
fun FeedbackBox(
    isDialogOpen: MutableState<Boolean>,
    date: String,
    userID: String,
    userType: Int,
    feedbackComment: MutableState<String>
) {
    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(10.dp)),
                color = Color(0xFF2D4263)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = "Leave Feedback",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    TextField(
                        value = feedbackComment.value,
                        onValueChange = { text ->
                            feedbackComment.value = text
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            backgroundColor = Color(0xFF191919)
                        ),
                        placeholder = { Text(text = "Enter your feedback...", color = Color.LightGray)},
                        modifier = Modifier.fillMaxHeight(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))


                    Button(
                        onClick = {
                            isDialogOpen.value = false
                            addFeedback(
                                uid = userID,
                                text = feedbackComment.value,
                                date = date
                            )
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Submit", fontSize = 16.sp, color = Color.Black)
                    }
                }
            }

        }
    }
}