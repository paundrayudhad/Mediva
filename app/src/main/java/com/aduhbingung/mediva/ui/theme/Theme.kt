package com.aduhbingung.mediva.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = MedivaPrimary,
    onPrimary = Color.White,
    primaryContainer = MedivaInfoBg,
    onPrimaryContainer = MedivaPrimary,
    secondary = MedivaAuthPrimary,
    onSecondary = Color.White,
    background = MedivaBackground,
    onBackground = MedivaTextPrimary,
    surface = MedivaSurface,
    onSurface = MedivaTextPrimary,
    surfaceVariant = MedivaSurfaceVariant,
    onSurfaceVariant = MedivaTextSecondary,
    outline = MedivaBorder
)

@Composable
fun MedivaTheme(
    // Per requirements: Light mode only
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                // Ensure light status bar icons
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}