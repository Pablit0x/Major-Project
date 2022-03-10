package com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.*
import com.example.moodtracker_jetpackcompose.ui.composables.screens.login.LoginViewModel
import com.example.moodtracker_jetpackcompose.ui.theme.hyperlinkColor
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.whiteBackground
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var signUpViewModel: SignUpViewModel
private lateinit var loginViewModel : LoginViewModel

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
            UsernameField(username = username, isError = usernameError)
            Spacer(modifier = Modifier.padding(5.dp))
            UserTypeField(userType = userType, isError = userTypeError)
            Spacer(modifier = Modifier.padding(5.dp))
            EmailField(email = email, isError =  emailError)
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(password = password, isError = passwordError)
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = {
                    emailError.value = !signUpViewModel.validateEmail(email = email.value)
                    passwordError.value = !signUpViewModel.validatePassword(password = password.value)
                    usernameError.value = !signUpViewModel.validateUsername(username = username.value)
                    userTypeError.value = !signUpViewModel.validateUserType(userType = userType.value)

                    if (!(usernameError.value && userTypeError.value && emailError.value && passwordError.value)) {
                        signUpViewModel.firebaseSignUp(
                            firebaseAuth = firebaseAuth,
                            username = username.value,
                            email = email.value,
                            userType = userType.value,
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

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}