package com.example.touska.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.touska.R

@Composable
fun failure(message:String,
            showRetry : Boolean = true,
            onretry : ()->Unit
            ) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(imageVector = Icons.Default.WifiOff, contentDescription = null,
                   modifier = Modifier.size(48.dp)
                )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, color = MaterialTheme.colors.surface)
            Spacer(modifier = Modifier.height(8.dp))
            if (showRetry) {
                Button(onClick = onretry) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }

    }

}