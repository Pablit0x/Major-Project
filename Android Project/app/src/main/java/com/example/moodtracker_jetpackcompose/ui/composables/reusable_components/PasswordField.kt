package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor

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
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
            else {

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password_eye),
                        "Password Visibility Identifier",
                        tint = if (passwordVisibility) Color.White else Color.DarkGray
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                "Lock Icon",
                tint = Color.White
            )
        },
        placeholder = { Text(text = "Password", color = Color.LightGray)},
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF191919),
            textColor = Color.White
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth(0.8f)
    )

    if (isError.value) {
        Text(
            text = "Password field cannot be empty!",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}