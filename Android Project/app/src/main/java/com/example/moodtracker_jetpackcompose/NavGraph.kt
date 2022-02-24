package com.example.moodtracker_jetpackcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moodtracker_jetpackcompose.ui.composables.AnimatedSplashScreen
import com.example.moodtracker_jetpackcompose.ui.composables.ForgotPasswordScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.login.LoginScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up.SignUpScreen

@Composable
fun SetupNavGraph(navController : NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(
            route = Screen.SplashScreen.route
        ){
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(route = Screen.ForgotPasswordScreen.route){
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route){
            SignUpScreen(navController = navController)
        }
    }
}