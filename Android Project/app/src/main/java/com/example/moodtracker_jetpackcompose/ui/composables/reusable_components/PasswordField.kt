package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectGray
import com.example.moodtracker_jetpackcompose.ui.theme.PerfectWhite


@Composable
fun PasswordField(password: MutableState<String>, isError: MutableState<Boolean>) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = password.value,
        onValueChange = { passwordValue ->
            password.value = passwordValue
            isError.value = false
        },
        trailingIcon = {

            if (isError.value)
                Icon(Icons.Filled.Warning, "error icon", tint = MaterialTheme.colors.error)
            else {

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_eye),
                        "password visibility identifier",
                        tint = if (passwordVisibility) PerfectWhite else Color.DarkGray
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock_closed),
                "lock icon",
                tint = PerfectWhite
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.password),
                color = Color.LightGray
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = PerfectGray,
            textColor = PerfectWhite
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )

    if (isError.value) {
        Text(
            text = stringResource(id = R.string.invalid_password_msg),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}