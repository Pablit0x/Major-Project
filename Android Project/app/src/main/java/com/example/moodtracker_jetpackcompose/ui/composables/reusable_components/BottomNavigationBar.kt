package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moodtracker_jetpackcompose.BottomBarScreen
import com.example.moodtracker_jetpackcompose.Screen

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.Calendar,
        BottomBarScreen.Home,
        BottomBarScreen.Messenger
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation{
        screens.forEach{ screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }

    }
}

@Composable
fun RowScope.AddItem(
    screen : BottomBarScreen,
    currentDestination : NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(
        label = {Text(text = screen.title)},
    icon = { Icon(imageVector = screen.icon, contentDescription = "Navigation Icon" )},
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}