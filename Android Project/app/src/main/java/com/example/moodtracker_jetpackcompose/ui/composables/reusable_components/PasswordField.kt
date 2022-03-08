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
    OutlinedTextField(
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
                        tint = if (passwordVisibility) primaryColor else Color.Gray
                    )
                }
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                "Lock Icon"
            )
        },
        label = { Text("Password") },
        placeholder = { Text(text = "Password") },
        singleLine = true,
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