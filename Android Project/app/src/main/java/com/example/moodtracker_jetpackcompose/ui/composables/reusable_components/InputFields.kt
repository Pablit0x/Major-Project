package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.secondaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.tertiaryColor

@Composable
fun EmailField(email: MutableState<String>, isError: MutableState<Boolean>) {
    OutlinedTextField(
        value = email.value,
        onValueChange = { emailValue ->
            email.value = emailValue
            isError.value = false
        },
        label = { Text(text = "Email Address") },
        placeholder = { Text(text = "Email Address") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        trailingIcon = {
            if (isError.value)
                Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colors.error)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_email),
                "Email Icon"
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

@Composable
fun UserTypeField(accountType: MutableState<String>, isError: MutableState<Boolean>) {

    var expanded by remember { mutableStateOf(false) }
    val userTypes = listOf("Regular", "Supervisor")
    var color by remember { mutableStateOf(tertiaryColor) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
    ) {
        OutlinedTextField(
            value = accountType.value,
            onValueChange = { accountTypeValue ->
                accountType.value = accountTypeValue
            },
            enabled = false,
            textStyle = TextStyle(color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold),
            colors = TextFieldDefaults.outlinedTextFieldColors(disabledTextColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coords ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coords.size.toSize()
                },
            placeholder = { Text("Select User Type") },
            trailingIcon = {
                Icon(icon, "contentDescription",
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
                    accountType.value = label
                    isError.value = false
                    expanded = false
                    color = if (accountType.value == "Regular") {
                        secondaryColor
                    } else {
                        primaryColor
                    }
                }) {
                    Text(text = label, textAlign = TextAlign.Center)
                }
            }
        }
    }
    if (isError.value) {
        Text(
            text = "Account type cannot be blank!",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
        )
    }
}