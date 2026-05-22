package com.rocket111185.barcodewallet.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF285E5B),
    secondary = Color(0xFF6A5E2C),
    tertiary = Color(0xFF6B4E71),
    background = Color(0xFFF7F7F2),
    surface = Color(0xFFFFFFFF),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8FCFCA),
    secondary = Color(0xFFD6C779),
    tertiary = Color(0xFFD6B6DD),
    background = Color(0xFF111412),
    surface = Color(0xFF181C1A),
)

@Composable
fun BarcodeWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val systemBarColor = colorScheme.background.toArgb()

            window.statusBarColor = systemBarColor
            window.navigationBarColor = systemBarColor

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
