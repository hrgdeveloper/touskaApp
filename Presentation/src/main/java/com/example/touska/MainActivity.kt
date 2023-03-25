package com.example.touska

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.local.sharedpref.PrefManager

import com.example.touska.navigation.Navigation
import com.example.touska.screens.login.loginScreen
import com.example.touska.screens.homeScreen.homeScreen
import com.example.touska.screens.mainScreen.mainScreen

import com.example.touska.ui.theme.TouskaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TouskaTheme {
                val navController = rememberNavController()
                val loginState = prefManager.getValue(PrefManager.IS_LOGIN,Boolean::class.java,false)
                val route = if (loginState) Navigation.Main.route else Navigation.Login.route

                NavHost(navController = navController, startDestination = route) {
                    composable(route = Navigation.Login.route) {
                        loginScreen(navController = navController)
                    }
                    composable(route=Navigation.Main.route) {
                        mainScreen()
                    }

                }
            }
        }
    }
}

