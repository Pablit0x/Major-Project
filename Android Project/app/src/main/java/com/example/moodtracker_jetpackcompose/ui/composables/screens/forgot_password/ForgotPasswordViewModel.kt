package com.example.moodtracker_jetpackcompose.ui.composables.screens.forgot_password

import android.util.Patterns
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {
    fun validateEmail(email: String): Boolean {
        return !(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty())
    }
}