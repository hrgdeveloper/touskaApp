package com.example.touska.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val cardBack: Color = Color.Unspecified,
    val divider_color : Color   = Color.Unspecified
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }


val MaterialTheme.customColorsPalette: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current


val OnLightCustomColorsPalette = CustomColorsPalette(
    cardBack = card_back_light,
    divider_color= devider_light

)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    cardBack = card_back_dark,
    divider_color= devider_dark
)