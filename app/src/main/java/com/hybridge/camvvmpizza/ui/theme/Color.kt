package com.hybridge.camvvmpizza.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val RedPizza = Color(0xFF6DB085)
val CreamBackground = Color(0xFFFFF8E1)
val GreenHighlight = Color(0xFF388E3C)
val DarkPrimary = Color(0xFF2F4141)
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkOnSurface = Color(0xFFE3E3E3)

val LightColors = lightColorScheme(
    primary = RedPizza,
    secondary = GreenHighlight,
    background = CreamBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    secondary = Color(0xFFA5D6A7),
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface
)
