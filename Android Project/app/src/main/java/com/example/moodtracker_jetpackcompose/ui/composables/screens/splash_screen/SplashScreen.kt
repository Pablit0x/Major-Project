package com.example.moodtracker_jetpackcompose.ui.composables.screens.splash_screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtracker_jetpackcompose.R
import com.example.moodtracker_jetpackcompose.Screen

import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.time.LocalDate

private lateinit var splashScreenViewModel: SplashScreenViewModel

@Composable
fun AnimatedSplashScreen(navController: NavController) {

    splashScreenViewModel = SplashScreenViewModel()
    val firebaseAuth = FirebaseAuth.getInstance()

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000)
        )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        splashScreenViewModel.checkLoginStatus(firebaseAuth = firebaseAuth, navController = navController)
    }
    SplashScreen(alpha = alphaAnim.value)
}

@Composable
fun SplashScreen(alpha : Float) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color(0xFF2D4263) else primaryColor)
            .fillMaxSize()
    ) {
        Image(
            painter = key(R.drawable.splash_screen) { painterResource(R.drawable.splash_screen) },
            contentDescription = null,
            modifier = Modifier
                .size(450.dp)
                .alpha(alpha = alpha),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(alpha = 1f)
}