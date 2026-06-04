package com.xumaohuai.mono.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val MonoNavDark = Color(0xFF212124)
val MonoPageBg = Color(0xFFDBE3E8)
val MonoText = Color(0xFF202124)
val MonoMuted = Color(0xFF7D858B)
val MonoAccent = Color(0xFF3778C2)

private val LightColors: ColorScheme = lightColorScheme(
    primary = MonoAccent,
    secondary = MonoNavDark,
    background = MonoPageBg,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = MonoText,
    onSurface = MonoText,
)

private val DarkColors: ColorScheme = darkColorScheme(
    primary = MonoAccent,
    secondary = MonoNavDark,
    background = MonoPageBg,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = MonoText,
    onSurface = MonoText,
)

@Composable
fun MonoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content,
    )
}
