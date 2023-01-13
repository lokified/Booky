package com.loki.booko.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green900,
    primaryVariant = Green500,
    secondary = Green900,

    background = Green300,
    surface = Green800,
    onPrimary = Green200,
    onSecondary = Green200,
    onBackground = Green900,
    onSurface = Green300,
)

private val LightColorPalette = lightColors(
    primary = Grey400,
    primaryVariant = Grey600,
    secondary = Grey600,

    background = Grey100,
    surface = Grey200,
    onPrimary = Grey900,
    onSecondary = Grey900,
    onBackground = Grey900,
    onSurface = Grey900,

)

@Composable
fun BookoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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