package com.example.touska.navigation

sealed class Navigation(val route : String) {
    object Main : Navigation("Main")
    object Login : Navigation("Login")
}
