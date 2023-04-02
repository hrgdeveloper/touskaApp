package com.example.touska.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.utils.mirror


@Composable
fun CustomTopAppbar(title: String, navController: NavController) {
    TopAppBar(
        backgroundColor = MaterialTheme.customColorsPalette.top_bar,
        title = { Text(text = title) },
        navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        modifier = Modifier.mirror(),
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        }
    )
}
