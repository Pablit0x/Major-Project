package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.data.model.Constants.REGULAR_USER
import com.example.moodtracker_jetpackcompose.data.model.Constants.SUPERVISOR_USER
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.ActivityTypeField
import com.example.moodtracker_jetpackcompose.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*

private lateinit var mRegularMainViewModel: RegularMainViewModel

@Composable
fun ShowAddActivityAlertDialog(
    isDialogOpen: MutableState<Boolean>,
    navController: NavController,
    date: String,
    userType: Int,
    userID: String
) {

    mRegularMainViewModel = RegularMainViewModel()
    val nameVal = remember { mutableStateOf("") }
    val activityType = remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }


    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
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
                        text = "Add Activity",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    OutlinedTextField(
                        value = nameVal.value,
                        onValueChange = { nameVal.value = it },
                        label = { Text(text = "Activity Name") },
                        placeholder = { Text(text = "Activity Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    ActivityTypeField(activityType = activityType)
                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {


                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                            colors = CheckboxDefaults.colors(secondaryColor),
                            modifier = Modifier
                                .scale(1.5f)
                                .padding(end = 8.dp)
                        )

                        Text(text = "Completed", fontSize = 20.sp)

                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                val uniqueID = UUID.randomUUID().toString()
                                val activity = Activity(
                                    name = nameVal.value,
                                    type = activityType.value,
                                    done = isChecked.value,
                                    id = uniqueID,
                                    createdBy = userType
                                )
                                mRegularMainViewModel.addActivity(
                                    activity = activity,
                                    uid = userID,
                                    date = date
                                )
                                isDialogOpen.value = false
                                when(userType){
                                    REGULAR_USER -> navController.navigate(Screen.RegularMainScreen.passDate(date = date))
                                    SUPERVISOR_USER -> navController.navigate(Screen.SupervisorViewScreen.passDateAndUID(date = date, uid = userID))
                                }
                            },
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth(0.5f)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(primaryColor)
                        ) {
                            Text(
                                text = "Add",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            onClick = {
                                isDialogOpen.value = false
                            },
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(primaryColor)
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }

}
