package com.smarthome.tv.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Ocean Professional palette
private val Primary = Color(0xFF2563EB)
private val Secondary = Color(0xFFF59E0B)
private val Error = Color(0xFFEF4444)
private val Background = Color(0xFFF9FAFB)
private val Surface = Color(0xFFFFFFFF)
private val TextColor = Color(0xFF111827)
private val SurfaceVariant = Color(0xFF2E3036)
private val OnSurfaceVariant = Color(0xFFD7E2FF)

private val LightColors = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    error = Error,
    background = Background,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color(0xFF1A1C20),
    onBackground = TextColor,
    onSurface = TextColor,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
)

private val DarkColors = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    error = Error,
    background = Color(0xFF0F1115),
    surface = Color(0xFF1A1C20),
    onPrimary = Color.White,
    onSecondary = Color(0xFF1A1C20),
    onBackground = Color(0xFFE5E7EB),
    onSurface = Color(0xFFE5E7EB),
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
)

// Fallback FontFamily resembling Figtree (uses default sans-serif on device)
private val figtreeFallback = FontFamily.SansSerif

private val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = figtreeFallback, fontWeight = FontWeight.SemiBold, fontSize = 64.sp, lineHeight = 76.8.sp),
    headlineLarge = TextStyle(fontFamily = figtreeFallback, fontWeight = FontWeight.Medium, fontSize = 40.sp, lineHeight = 48.sp),
    titleLarge = TextStyle(fontFamily = figtreeFallback, fontWeight = FontWeight.Medium, fontSize = 28.sp, lineHeight = 36.sp),
    bodyLarge = TextStyle(fontFamily = figtreeFallback, fontWeight = FontWeight.Normal, fontSize = 18.sp),
    labelLarge = TextStyle(fontFamily = figtreeFallback, fontWeight = FontWeight.Medium, fontSize = 16.sp),
)

/**
 * PUBLIC_INTERFACE
 * SmartHomeTVTheme applies the Ocean Professional color scheme with typography
 * across the entire app.
 */
@Composable
fun SmartHomeTVTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}
