package com.hybridge.camvvmpizza.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val RedPizza = Color(0xFF6DB085)
val CreamBackground = Color(0xFFFFF8E1)
val GreenHighlight = Color(0xFF388E3C)

val LightColors = lightColorScheme(
    primary = RedPizza,
    secondary = GreenHighlight,
    background = CreamBackground,
    onPrimary = Color.White,
    onBackground = Color.Black,


)

val DarkColors = darkColorScheme(
    primary = Color(0xFF2F4141),
    secondary = Color(0xFFA5D6A7),
    background = Color(0xFF212121),
    onPrimary = Color.Black,
    onBackground = Color.White,

)