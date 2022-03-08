package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.moodtracker_jetpackcompose.R

@Composable
fun UsernameField(username: MutableState<String>, isError: MutableState<Boolean>) {
    OutlinedTextField(
        value = username.value,
        onValueChange = { usernameValue ->
            username.value = usernameValue
            isError.value = false
        },
        label = { Text(text = "Username") },
        placeholder = { Text(text = "Username") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                "Person Icon"
            )
        },
    )
    if (isError.value) {
        Text(
            text = "Username field cannot be empty!",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}