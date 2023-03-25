package com.example.touska.navigation

sealed class MainNavigation(val route : String) {
    object Home : MainNavigation("Home")
    object Report : MainNavigation("Report")
    object Setting : MainNavigation("Setting")
}
