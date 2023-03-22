package com.example.touska

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.touska.navigation.Navigation
import com.example.touska.screens.login.loginScreen

import com.example.touska.ui.theme.TouskaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TouskaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Navigation.Login.route) {
                    composable(route = Navigation.Login.route) {
                        loginScreen(navController = navController)
                    }

                }
            }
        }
    }
}

