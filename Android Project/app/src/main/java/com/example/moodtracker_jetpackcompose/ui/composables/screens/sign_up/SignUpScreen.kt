package com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.EmailField
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.PasswordField
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UserTypeField
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.UsernameField
import com.example.moodtracker_jetpackcompose.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var signUpViewModel: SignUpViewModel

@Composable
fun SignUpScreen(navController: NavController) {

    firebaseAuth = FirebaseAuth.getInstance()
    signUpViewModel = SignUpViewModel()

    val username: MutableState<String> = remember { mutableStateOf("") }
    val userType: MutableState<String> = remember { mutableStateOf("") }
    val email: MutableState<String> = remember { mutableStateOf("") }
    val password: MutableState<String> = remember { mutableStateOf("") }

    val usernameError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val userTypeError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }

    val image = painterResource(id = R.drawable.ic_launcher_foreground)


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .background(color = primaryColor)
        ) {
            Image(
                painter = image, contentDescription = "Application Logo", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
            UsernameField(username, usernameError)
            Spacer(modifier = Modifier.padding(5.dp))
            UserTypeField(userType, userTypeError)
            Spacer(modifier = Modifier.padding(5.dp))
            EmailField(email, emailError)
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(password, passwordError)
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = {
//                    if(validateData(
//                        username.value,
//                        usernameError,
//                        email.value,
//                        emailError,
//                        password.value,
//                        passwordError,
//                        accountType.value,
//                        accountTypeError
//                    )
//                    ){
//                        firebaseSignUp(email.value, password.value, navController)
//                    }
                    emailError.value = !signUpViewModel.validateEmail(email = email.value)
                    passwordError.value =
                        !signUpViewModel.validatePassword(password = password.value)
                    usernameError.value =
                        !signUpViewModel.validateUsername(username = username.value)
                    userTypeError.value =
                        !signUpViewModel.validateUserType(userType = userType.value)

                    Log.e("Username", usernameError.value.toString())
                    Log.e("UserType", userTypeError.value.toString())
                    Log.e("Email", emailError.value.toString())
                    Log.e("Password", passwordError.value.toString())

                    if (!(usernameError.value && userTypeError.value && emailError.value && passwordError.value)) {
                        Log.e("Register", "Registering")
                        signUpViewModel.firebaseSignUp(
                            firebaseAuth = firebaseAuth,
                            email = email.value,
                            password = password.value,
                            navController = navController
                        )
                    }

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


//private fun validateData(
//    username: String,
//    usernameError: MutableState<Boolean>,
//    email: String,
//    emailError: MutableState<Boolean>,
//    password: String,
//    passwordError: MutableState<Boolean>,
//    accountType: String,
//    accountTypeError: MutableState<Boolean>,
//) : Boolean {
//    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//        emailError.value = true
//        return false
//    }
//    if (password.isEmpty()) {
//        passwordError.value = true
//        return false
//    }
//    if (username.isEmpty()) {
//        usernameError.value = true
//        return false
//    }
//    return if (accountType.isEmpty()) {
//        accountTypeError.value = true
//        false
//    }
//    else
//    {
//        true
//    }
//}
//
//fun firebaseSignUp(email: String, password: String, navController: NavController) {
//    firebaseAuth.createUserWithEmailAndPassword(email, password)
//        .addOnSuccessListener {
//            navController.navigate(Screen.LoginScreen.route)
//            Log.e("Sign Up", "UserCreated")
//        }
//        .addOnFailureListener {
//            Log.e("Sign Up", "Fail")
//        }
//}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}