package com.example.better_me

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.better_me.nav.SetupNavGraph
import com.example.better_me.ui.theme.BetterMe_Theme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BetterMe_Theme {
                SetupNavGraph()
            }
        }
    }
}