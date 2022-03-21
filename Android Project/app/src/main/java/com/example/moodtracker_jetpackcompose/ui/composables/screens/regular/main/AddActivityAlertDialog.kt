package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.add_activity

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.data.model.Activity
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.ActivityTypeField
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.AddActivityViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import java.util.*

private lateinit var addActivityViewModel: AddActivityViewModel

@Composable
fun ShowAlertDialog(isDialogOpen: MutableState<Boolean>, navController: NavController) {

    val firebaseUser = FirebaseAuth.getInstance().currentUser
    addActivityViewModel = AddActivityViewModel()
    val nameVal = remember { mutableStateOf("") }
    val activityType = remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }


    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
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
                        fontSize = 25.sp
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    OutlinedTextField(
                        value = nameVal.value,
                        onValueChange = { nameVal.value = it },
                        label = { Text(text = "Activity Name") },
                        placeholder = { Text(text = "Activity Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    ActivityTypeField(activityType = activityType)
                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Completed:", fontSize = 20.sp)

                        Spacer(modifier = Modifier.padding(start = 8.dp, end = 8.dp))

                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it },
                            colors = CheckboxDefaults.colors(secondaryColor),
                            modifier = Modifier.scale(1.5f)
                        )
                    }

                    Spacer(modifier = Modifier.padding(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                val uniqueID = UUID.randomUUID().toString()
                                val activity = Activity(
                                    nameVal.value,
                                    activityType.value,
                                    done = isChecked.value,
                                    id = uniqueID
                                )
                                if(activity != null){
                                    addActivityViewModel.addActivity(activity, firebaseUser!!.uid)
                                }
                                isDialogOpen.value = false
                                navController.navigate(Screen.RegularMainScreen.route)
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
