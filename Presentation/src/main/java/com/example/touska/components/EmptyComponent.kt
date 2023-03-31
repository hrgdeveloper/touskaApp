package com.example.touska.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.touska.R

@Composable
fun empty(message:String,
            ) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(imageVector = Icons.Default.List, contentDescription = null,
                   modifier = Modifier.size(48.dp)
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, color = MaterialTheme.colors.surface)

        }

    }

}