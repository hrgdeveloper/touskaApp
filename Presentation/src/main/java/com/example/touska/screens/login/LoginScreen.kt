package com.example.touska.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun loginScreen( navController: NavController,
                loginViewModel: LoginViewModel = hiltViewModel()
                ){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "LoginPage")
    }

}