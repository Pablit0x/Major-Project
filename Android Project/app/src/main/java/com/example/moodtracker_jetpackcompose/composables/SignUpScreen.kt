package com.example.moodtracker_jetpackcompose.composables

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth : FirebaseAuth

@Composable
fun SignUpScreen(navController: NavController) {

    firebaseAuth = FirebaseAuth.getInstance()

    val email : MutableState<String> = remember { mutableStateOf("")}
    val password : MutableState<String> = remember{ mutableStateOf("")}

    val auth: FirebaseAuth
    val image = painterResource(id = R.drawable.ic_launcher_foreground)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(color = primaryColor)
        ) {
            Image(
                painter = image, contentDescription = "Application Logo", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "Sign Up",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.padding(15.dp))
            NameField()
            Spacer(modifier = Modifier.padding(5.dp))
            UserTypeField()
            Spacer(modifier = Modifier.padding(5.dp))
            EmailField(email)
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(password)
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = {
                          validateData(email.value, password.value)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Sign Up", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Back to Sign in",
                    fontSize = 16.sp,
                    color = hyperlinkColor,
                    modifier = Modifier.clickable { navController.popBackStack() })
            }
        }
    }
}


@Composable
fun NameField() {
    var nameValue by remember { mutableStateOf("") }
    OutlinedTextField(
        value = nameValue,
        onValueChange = { nameValue = it },
        label = { Text(text = "Name") },
        placeholder = { Text(text = "Name") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                "Person Icon"
            )
        },
    )
}

private fun validateData(email : String, password : String){
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        Log.e("Email Register", "Invalid Email format")
    } else if(password.isEmpty()){
        Log.e("Password Register", "Password not entered")
    } else if(password.length < 6){
        Log.e("Password Register", "Password must be at least 6 characters long")
    } else{
        firebaseSignUp(email, password)
    }
}

fun firebaseSignUp(email : String, password : String) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            Log.e("Sign Up", "UserCreated")
        }
        .addOnFailureListener{
            Log.e("Sign Up", "Fail")
        }
}

@Composable
fun UserTypeField() {

    var expanded by remember { mutableStateOf(false) }
    val userTypes = listOf("Regular", "Supervisor")
    var color by remember{ mutableStateOf(tertiaryColor)}
    var selectedType by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier
        .fillMaxWidth(0.8f)) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {
                selectedType = it
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
            placeholder = {Text("Select User Type")},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
        ) {
            userTypes.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedType = label
                    expanded = false
                    color = if(selectedType == "Regular"){
                        secondaryColor
                    } else{
                        primaryColor
                    }
                }) {
                    Text(text = label, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}