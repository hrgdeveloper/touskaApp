package com.example.touska.screens.mainScreen

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.touska.navigation.BottomNavItem

import com.example.touska.navigation.MainNavigation
import com.example.touska.navigation.Navigation
import com.example.touska.screens.homeScreen.homeScreen
import com.example.touska.screens.login.loginScreen
import com.example.touska.screens.reportScreen.reportScreen
import com.example.touska.screens.settingScreen.settingScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
            navController
            )
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem(Icons.Default.Home,"Home",MainNavigation.Home.route,

            ),
        BottomNavItem(Icons.Default.Report,"Report",MainNavigation.Report.route,

            ),
        BottomNavItem(Icons.Default.Settings,"Settings",MainNavigation.Setting.route,

            )
    )
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 2.dp
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()

        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                label = { Text(text = item.name,
                    fontSize = 12.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = selected,
                onClick = {
                 //   navController.navigate(item.route)

                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = MainNavigation.Home.route) {
        composable(route = MainNavigation.Home.route) {
            homeScreen(navController = navController)
        }

        composable(route = MainNavigation.Report.route) {
            reportScreen(navController = navController)
        }

        composable(route = MainNavigation.Setting.route) {
            settingScreen(navController = navController)
        }

    }
}
