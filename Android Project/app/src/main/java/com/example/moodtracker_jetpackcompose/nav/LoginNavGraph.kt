package com.example.moodtracker_jetpackcompose.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moodtracker_jetpackcompose.UserBottomBarScreen
import com.example.moodtracker_jetpackcompose.Screen
import com.example.moodtracker_jetpackcompose.SupervisorBottomBarScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.splash_screen.AnimatedSplashScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.forgot_password.ForgotPasswordScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.login.LoginScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.calendar.CalendarScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main.RegularMainScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.messenger.MessengerScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.sign_up.SignUpScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.SupervisorCalendarScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.SupervisorMainScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.main.SupervisorViewScreen
import com.example.moodtracker_jetpackcompose.ui.composables.screens.supervisor.requests.RequestsScreen

@Composable
fun SetupNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController = navController)
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = Screen.RegularMainScreen.route,
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            RegularMainScreen(navController = navController, it.arguments?.getString("date")!!)
        }
        composable(route = Screen.SupervisorMainScreen.route) {
            SupervisorMainScreen(navController = navController)
        }
        composable(
            route = UserBottomBarScreen.Home.route,
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            RegularMainScreen(navController = navController, it.arguments?.getString("date")!!)
            Log.e("Arg", it.arguments?.getString("date")!!)
        }
        composable(route = UserBottomBarScreen.Calendar.route) {
            CalendarScreen(navController = navController)
        }
//        composable(route = UserBottomBarScreen.Messenger.route) {
//            MessengerScreen(navHostController = navController)
//        }
        composable(route = SupervisorBottomBarScreen.Requests.route) {
            RequestsScreen(navController = navController)
        }
        composable(route = SupervisorBottomBarScreen.Home.route) {
            SupervisorMainScreen(navController = navController)
        }
//        composable(route = SupervisorBottomBarScreen.Messenger.route) {
//            MessengerScreen(navHostController = navController)
//        }
        composable(
            route = Screen.SupervisorCalendarScreen.route,
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                    defaultValue = ""
                }, navArgument(
                    "uid"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            SupervisorCalendarScreen(
                navController = navController,
                username = it.arguments?.getString("username")!!,
                userUID = it.arguments?.getString("uid")!!
            )
        }
        composable(
            route = Screen.SupervisorViewScreen.route,
            arguments = listOf(navArgument("date") {
                type = NavType.StringType
                defaultValue = ""
            }, navArgument("uid") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            SupervisorViewScreen(
                navController = navController,
                selectedDate = it.arguments?.getString("date")!!,
                it.arguments?.getString("uid")!!
            )
        }
    }
}