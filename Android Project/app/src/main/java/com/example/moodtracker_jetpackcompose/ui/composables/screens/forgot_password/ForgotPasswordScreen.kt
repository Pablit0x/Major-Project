package com.example.moodtracker_jetpackcompose.ui.composables.screens.forgot_password

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.google.firebase.auth.FirebaseUser

private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
private lateinit var firebaseAuth: FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController) {

    forgotPasswordViewModel = ForgotPasswordViewModel()
    firebaseAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val image = painterResource(id = R.drawable.ic_launcher_foreground)
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
                .background(color = primaryColor)
        ) {
            Image(
                painter = image, contentDescription = "Application Logo", modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {
            Text(
                text = "Forgot your password?",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp
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
                fontSize = 14.sp
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
                }
            ) {
                Text(text = "Submit", fontSize = 20.sp)
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
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(navController = rememberNavController())
}