package com.example.moodtracker_jetpackcompose.ui.composables.reusable_components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moodtracker_jetpackcompose.SupervisorBottomBarScreen
import com.example.moodtracker_jetpackcompose.UserBottomBarScreen

@Composable
fun UserBottomBar(navController: NavHostController){
    val screens = listOf(
        UserBottomBarScreen.Calendar,
        UserBottomBarScreen.Home,
        UserBottomBarScreen.Messenger
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation{
        screens.forEach{ screen ->
            AddUserItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }

    }
}

@Composable
fun RowScope.AddUserItem(
    screen : UserBottomBarScreen,
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


@Composable
fun SupervisorBottomBar(navController: NavHostController){
    val screens = listOf(
        SupervisorBottomBarScreen.Requests,
        SupervisorBottomBarScreen.Home,
        SupervisorBottomBarScreen.Messenger
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation{
        screens.forEach{ screen ->
            AddSupervisorItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }

    }
}

@Composable
fun RowScope.AddSupervisorItem(
    screen : SupervisorBottomBarScreen,
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