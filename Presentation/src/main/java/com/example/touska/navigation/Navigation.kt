package com.example.touska.navigation

sealed class Navigation(val route : String) {
    object Home : Navigation("Home")
    object Login : Navigation("Login")
}
