package com.example.touska

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.di.PrefModule
import com.example.data.local.sharedpref.PrefManager
import com.example.touska.navigation.MainNavigation

import com.example.touska.navigation.Navigation
import com.example.touska.screens.login.loginScreen
import com.example.touska.screens.homeScreen.homeScreen
import com.example.touska.screens.mainScreen.mainScreen

import com.example.touska.ui.theme.TouskaTheme
import com.example.touska.utils.LocaleHelper
import com.example.touska.utils.ThemeTypes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val systemUiController = rememberSystemUiController()


            var isDarkTheme = when (mainViewModel.them.value) {
                ThemeTypes.LIGHT -> {
                    false
                }
                ThemeTypes.DARK -> {
                    true
                }
                else ->{
                    isSystemInDarkTheme()
                }

            }

            TouskaTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                systemUiController.isStatusBarVisible=false
                systemUiController.isStatusBarVisible = when (navBackStackEntry.value?.destination?.route) {
                    Navigation.Login.route -> false //
                    else -> true // in all other cases show bottom bar
                }


                val loginState = prefManager.getValue(PrefManager.IS_LOGIN, Boolean::class, false)
                val route = if (loginState) Navigation.Main.route else Navigation.Login.route

                NavHost(navController = navController, startDestination = route) {
                    composable(route = Navigation.Login.route) {
                        loginScreen(navController = navController)
                    }
                    composable(route = Navigation.Main.route) {
                        mainScreen(mainViewModel)
                    }

                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.setLocale(newBase, MyApp.instance.language)
        )
    }



}

