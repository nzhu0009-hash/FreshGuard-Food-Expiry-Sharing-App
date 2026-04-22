package com.example.foodwasteapplication.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LeafGreenDark,
    secondary = SageGreenDark,
    tertiary = SoftCoralDark,
    background = ForestBackground,
    surface = ForestSurface,
    primaryContainer = Moss,
    secondaryContainer = LeafGreen,
    onPrimary = ForestBackground,
    onBackground = Cream,
    onSurface = Cream,
    onSurfaceVariant = Mist
)

private val LightColorScheme = lightColorScheme(
    primary = LeafGreen,
    secondary = SageGreen,
    tertiary = SoftCoral,
    background = Cream,
    surface = androidx.compose.ui.graphics.Color.White,
    primaryContainer = Mist,
    secondaryContainer = SageGreen.copy(alpha = 0.35f),
    tertiaryContainer = SoftCoral.copy(alpha = 0.24f),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onBackground = Moss,
    onSurface = Moss,
    onSurfaceVariant = Moss.copy(alpha = 0.78f)
)

private val AppShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(36.dp)
)

@Composable
fun FoodwasteApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
