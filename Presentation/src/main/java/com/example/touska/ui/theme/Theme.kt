package com.example.touska.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = textColor_dark,
    primaryVariant = primaryVariant,
    secondary = secondary,
    surface = gray_dark,
    background = background_dark

)

private val LightColorPalette = lightColors(
    primary = textColor_light,
    primaryVariant = primaryVariant,
    secondary = secondary,
    surface = gray_light,
    background = background_light



)

@Composable
fun TouskaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}