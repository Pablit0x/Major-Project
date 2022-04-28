package com.example.better_me.ui.composables.screens.forgot_password

import android.util.Patterns
import androidx.lifecycle.ViewModel

/**
 * ForgotPasswordScreen ViewModel class used to validate user input
 */
class ForgotPasswordViewModel : ViewModel() {
    /**
     * This function validates if the given input is a valid email
     * @param email String variable that represents email
     * @return Boolean variable that represents if the given email is valid or not
     */
    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }
}