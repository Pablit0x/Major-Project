package com.example.moodtracker_jetpackcompose

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object SignUpScreen : Screen(route = "sign_up_screen")
    object ForgotPasswordScreen : Screen(route = "forgot_password_screen")
    object RegularMainScreen : Screen(route = "regular_main_screen?date={date}") {
        fun passDate(date: String): String {
            return "regular_main_screen?date=$date"
        }
    }
    object SupervisorMainScreen : Screen(route = "supervisor_main_screen")
    object SupervisorCalendarScreen :
        Screen(route = "supervisor_calendar_screen?uid={uid}&date={date}") {
        fun passUID(uid: String): String {
            return "supervisor_calendar_screen?uid=$uid"
        }
    }

    object SupervisorViewScreen : Screen(route = "supervisor_view_screen?uid={uid}&date={date}") {
        fun passDateAndUID(uid: String, date: String): String {
            return "supervisor_view_screen?uid=$uid&date=$date"
        }
    }
}
