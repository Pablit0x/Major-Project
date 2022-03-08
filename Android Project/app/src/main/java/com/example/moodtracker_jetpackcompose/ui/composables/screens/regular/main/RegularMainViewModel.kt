package com.example.moodtracker_jetpackcompose.ui.composables.screens.regular.main

import androidx.lifecycle.ViewModel

class RegularMainViewModel : ViewModel() {

    fun getUserName(email: String): String {
        return email
    }
}