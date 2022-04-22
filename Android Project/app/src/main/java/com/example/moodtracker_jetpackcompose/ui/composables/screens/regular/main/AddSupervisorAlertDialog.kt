package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.EmailField
import com.example.moodtracker_jetpackcompose.ui.theme.NavyBlue
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectBlack
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite


@Composable
fun ShowAddSupervisorDialog(isDialogOpen: MutableState<Boolean>) {
    val email = remember { mutableStateOf("") }
    val regularMainViewModel = RegularMainViewModel()

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.60f)
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
                        text = stringResource(id = R.string.add_supervisor),
                        color = PerfectWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    EmailField(email = email, isError = mutableStateOf(false))

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            regularMainViewModel.addSupervisor(email = email.value)
                            isDialogOpen.value = false
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .fillMaxWidth(0.8f)
                            .padding(10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(PerfectWhite)
                    ) {
                        Text(
                            text = stringResource(id = R.string.send_request),
                            color = PerfectBlack,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }

}
