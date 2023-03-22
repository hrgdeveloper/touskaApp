package com.example.touska.components
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.touska.ui.theme.spacing

@Composable
fun VerticalDefaultMargin(){
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin))
}
@Composable
fun VerticalDefaultMarginBigger(){
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin_bigger))
}
