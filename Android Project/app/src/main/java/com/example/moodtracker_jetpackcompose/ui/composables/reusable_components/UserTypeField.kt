package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectGray
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite
import com.example.moodtracker_jetpackcompose.ui.theme.RedRose


@Composable
fun UserTypeField(userType: MutableState<String>, isError: MutableState<Boolean>) {

    var expanded by remember { mutableStateOf(false) }
    val userTypes = listOf("Regular", "Supervisor")
    var userTypeTextColor by remember { mutableStateOf(PerfectWhite) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val arrowIcon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        TextField(
            value = userType.value,
            onValueChange = { accountTypeValue ->
                userType.value = accountTypeValue
            },
            enabled = false,
            textStyle = TextStyle(
                color = userTypeTextColor,
                fontSize = 20.sp,
            ),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = PerfectWhite,
                backgroundColor = PerfectGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
                .onGloballyPositioned
                { coords ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coords.size.toSize()
                },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.select_user_type),
                    color = Color.LightGray
                )
            },
            trailingIcon = {
                Icon(arrowIcon, "arrow icon",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            userTypes.forEach { label ->
                DropdownMenuItem(onClick = {
                    userType.value = label
                    isError.value = false
                    expanded = false
                    userTypeTextColor = if (userType.value == "Regular") {
                        PerfectWhite
                    } else {
                        RedRose
                    }
                }) {
                    Text(
                        text = label,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
    if (isError.value) {
        Text(
            text = stringResource(id = R.string.invalid_account_type),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}