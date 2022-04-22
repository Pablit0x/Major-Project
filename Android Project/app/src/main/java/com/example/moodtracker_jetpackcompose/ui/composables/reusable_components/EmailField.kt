package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.moodtracker_jetpackcompose.R

@Composable
fun EmailField(email: MutableState<String>, isError: MutableState<Boolean>) {
    TextField(
        value = email.value,
        onValueChange = { emailValue ->
            email.value = emailValue
            isError.value = false
        },
        placeholder = { Text(text = "Enter Email...", color = Color.LightGray) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            backgroundColor = Color(0xFF191919)
        ),
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                "Email Icon", tint = Color.White
            )
        },
    )
    if (isError.value) {
        Text(
            text = "Invalid E-mail address!",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}