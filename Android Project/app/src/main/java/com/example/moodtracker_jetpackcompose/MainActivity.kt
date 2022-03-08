package com.example.moodtracker_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.moodtracker_jetpackcompose.nav.SetupNavGraph
import com.example.moodtracker_jetpackcompose.ui.theme.MoodTracker_JetpackComposeTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTracker_JetpackComposeTheme {
                SetupNavGraph()
            }
        }
    }
}