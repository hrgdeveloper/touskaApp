package com.example.touska.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.utils.mirror


@Composable
fun CustomTopAppbar(title: String, navController: NavController,elevation : Dp = 4.dp) {
    TopAppBar(
        backgroundColor = MaterialTheme.customColorsPalette.top_bar,
        title = { Text(text = title) },
        elevation = elevation,
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
