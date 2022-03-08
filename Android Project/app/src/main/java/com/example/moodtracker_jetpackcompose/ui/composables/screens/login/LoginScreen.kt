package com.example.moodtracker_jetpackcompose.ui.composables.screens.login

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.EmailField
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.PasswordField
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.ProgressBar
import com.example.moodtracker_jetpackcompose.ui.theme.hyperlinkColor
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.whiteBackground
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var loginViewModel: LoginViewModel
private lateinit var database: DatabaseReference

@Composable
fun LoginScreen(navController: NavController) {

    firebaseAuth = FirebaseAuth.getInstance()
    loginViewModel = LoginViewModel()


    val email: MutableState<String> = remember { mutableStateOf("") }
    val password: MutableState<String> = remember { mutableStateOf("") }

    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }

    val image = painterResource(id = R.drawable.ic_launcher_foreground)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(color = primaryColor)
        ) {
            Image(
                painter = image, contentDescription = "Application Logo", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(20.dp))
            EmailField(email = email, isError = emailError)
            PasswordField(password = password, isError = passwordError)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    emailError.value = !loginViewModel.validateEmail(email = email.value)
                    if (loginViewModel.validateEmail(email = email.value)) {
                        loginViewModel.firebaseLogin(
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
                Text(text = "Sign In", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.sign_up),
                    fontSize = 16.sp,
                    color = hyperlinkColor,
                    modifier = Modifier.clickable { navController.navigate(Screen.SignUpScreen.route) })
                Text(stringResource(id = R.string.forgot_password),
                    fontSize = 16.sp,
                    color = hyperlinkColor,
                    modifier = Modifier.clickable { navController.navigate(Screen.ForgotPasswordScreen.route) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}