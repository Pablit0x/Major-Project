package com.example.moodtracker_jetpackcompose

sealed class Screen(val route : String){
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")
    object ForgotPasswordScreen : Screen(route ="forgot_password_screen")
}
