package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.data.model.addFeedback
import com.example.moodtracker_jetpackcompose.ui.theme.NavyBlue
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectGray
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite

@Composable
fun FeedbackBox(
    isDialogOpen: MutableState<Boolean>,
    date: String,
    userID: String,
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
                    .border(width = 1.dp, color = PerfectWhite, shape = RoundedCornerShape(10.dp)),
                color = NavyBlue
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = stringResource(id = R.string.leave_feedback),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = PerfectWhite
                    )
                    Spacer(modifier = Modifier.padding(5.dp))

                    TextField(
                        value = feedbackComment.value,
                        onValueChange = { text ->
                            feedbackComment.value = text
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = PerfectWhite,
                            backgroundColor = PerfectGray
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.enter_feedback),
                                color = Color.LightGray
                            )
                        },
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