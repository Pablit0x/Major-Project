package com.example.better_me.ui.composables.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.better_me.Screen
import com.example.better_me.ui.composables.reusable_components.EmailField
import com.example.better_me.ui.composables.reusable_components.PasswordField
import com.example.better_me.ui.theme.NavyBlue
import com.example.better_me.ui.theme.Black
import com.example.better_me.ui.theme.PerfectGray
import com.example.better_me.ui.theme.White
import com.example.better_me.R
import com.google.firebase.auth.FirebaseAuth

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var loginViewModel: LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {

    firebaseAuth = FirebaseAuth.getInstance()
    loginViewModel = LoginViewModel()


    val email: MutableState<String> = remember { mutableStateOf("") }
    val password: MutableState<String> = remember { mutableStateOf("") }

    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val passwordError: MutableState<Boolean> = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(PerfectGray)
        ) {
            Image(
                painter = key(R.drawable.splash_screen) { painterResource(R.drawable.splash_screen) },
                contentDescription = "App Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(330.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(PerfectGray)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(NavyBlue)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_in),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = White,
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.padding(20.dp))
            EmailField(email = email, isError = emailError)
            PasswordField(password = password, isError = passwordError)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    emailError.value = !loginViewModel.validateEmail(email = email.value)
                    passwordError.value =
                        !loginViewModel.validatePassword(password = password.value)
                    if (!emailError.value && !passwordError.value) {
                        loginViewModel.firebaseLogin(
                            firebaseAuth = firebaseAuth,
                            email = email.value,
                            password = password.value,
                            navController = navController
                        )
                    }
                }, colors = ButtonDefaults.buttonColors(White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    fontSize = 20.sp,
                    color = Black
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.sign_up),
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier.clickable { navController.navigate(Screen.SignUpScreen.route) })
                Text(stringResource(id = R.string.forgot_password),
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier.clickable { navController.navigate(Screen.ForgotPasswordScreen.route) })
            }
        }
    }
}