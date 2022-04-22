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
    var msg by remember {
        mutableStateOf("")
    }
    var buttonText by remember{ mutableStateOf("")}
    var textFieldState by remember { mutableStateOf(true) }
    when (userType) {
        REGULAR_USER -> {
            msg = "Feedback"
            textFieldState = false
            buttonText = "Close"
        }
        SUPERVISOR_USER -> {
            msg = "Leave Feedback"
            textFieldState = true
            buttonText = "Submit"
        }
    }

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
//                    .fillMaxHeight(0.8f)
                    .padding(5.dp)
                    .border(width = 1.dp, color = primaryColor, shape = RoundedCornerShape(10.dp)),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = msg,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    OutlinedTextField(
                        value = feedbackComment.value,
                        onValueChange = { text ->
                            feedbackComment.value = text
                        },
                        enabled = textFieldState,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black),
                        placeholder = { Text(text = "Enter your feedback...") },
//                        modifier = Modifier.fillMaxHeight(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(5.dp))


                    Button(
                        onClick = {
                            isDialogOpen.value = false
                            if(userType == REGULAR_USER){
//                                navController.navigate(Screen.RegularMainScreen.passDate(date))
                                isDialogOpen.value = false
                            }
                            else{
                                addFeedback(
                                    uid = userID,
                                    text = feedbackComment.value,
                                    date = date
                                )
                                isDialogOpen.value = false
                            }
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(primaryColor)
                    ) {
                        Text(text = buttonText, fontSize = 16.sp)
                    }
                }
            }

        }
    }
}