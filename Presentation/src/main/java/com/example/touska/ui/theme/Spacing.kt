package com.example.touska.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default_margin : Dp  = 16.dp,
    val default_margin_bigger : Dp  = 24.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing : Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current