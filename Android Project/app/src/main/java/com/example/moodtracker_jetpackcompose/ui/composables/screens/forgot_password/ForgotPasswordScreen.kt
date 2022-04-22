package com.example.moodtracker_jetpackcompose.ui.composables.screens.forgot_password

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.reusable_components.EmailField
import com.example.moodtracker_jetpackcompose.ui.theme.hyperlinkColor
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.example.moodtracker_jetpackcompose.ui.theme.whiteBackground
import com.google.firebase.auth.FirebaseAuth

private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    forgotPasswordViewModel = ForgotPasswordViewModel()
    firebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val email: MutableState<String> = remember { mutableStateOf("") }
    val emailError: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(Color(0xFF191919))
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0xFF191919))
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color(0xFF2D4263))
                .padding(10.dp)
        ) {
            Text(
                text = "Forgot your password?",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "Please enter the email you use to sign in to the Mood Tracker.",
                style = TextStyle(
                    fontWeight = FontWeight.Light,
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(15.dp))
            EmailField(email, emailError)
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                onClick = {
                    if (forgotPasswordViewModel.validateEmail(email = email.value)) {
                        firebaseAuth.sendPasswordResetEmail(email.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "We have sent a password recovery instructions to your email.", Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                                else{
                                    Toast.makeText(
                                        context,
                                        task.exception!!.message.toString(), Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Submit", fontSize = 20.sp, color = Color.Black)
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Back to Sign in",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.clickable { navController.popBackStack() })
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}