package com.example.moodtracker_jetpackcompose.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodtracker_jetpackcompose.BottomBarScreen
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.splash_screen.AnimatedSplashScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.forgot_password.ForgotPasswordScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.login.LoginScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar.CalendarScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.RegularMainScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.messenger.MessengerScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up.SignUpScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.SupervisorMainScreen

@Composable
fun SetupNavGraph(){
    val navController = rememberNavController()
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
         composable(route = Screen.RegularMainScreen.route,
         arguments = listOf(navArgument("date"){
             type = NavType.StringType
             defaultValue = ""
         })){
             RegularMainScreen(navController = navController, it.arguments?.getString("date")!!)
         }
        composable(route = Screen.SupervisorMainScreen.route){
            SupervisorMainScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Home.route,
            arguments = listOf(navArgument("date"){
                type = NavType.StringType
                defaultValue = ""
            })){
            RegularMainScreen(navController = navController, it.arguments?.getString("date")!!)
            Log.e("Arg", it.arguments?.getString("date")!!)
        }
        composable(route = BottomBarScreen.Calendar.route){
            CalendarScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Messenger.route){
            MessengerScreen(navHostController = navController)
        }
    }
}