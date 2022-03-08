package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moodtracker_jetpackcompose.ui.theme.primaryColor

@Composable
fun ProgressBar(isDisplayed: Boolean) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            CircularProgressIndicator(color = primaryColor)
        }
    }
}