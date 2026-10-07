package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = GreenPrimary,
  onPrimary = PureWhite,
  primaryContainer = GreenContainer,
  onPrimaryContainer = OnGreenContainer,
  secondary = GreenLight,
  onSecondary = PureWhite,
  background = PureWhite,
  onBackground = TextPrimary,
  surface = PureWhite,
  onSurface = TextPrimary,
  surfaceVariant = SurfaceGray,
  onSurfaceVariant = TextSecondary,
  outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
  primary = GreenLight,
  onPrimary = PureWhite,
  primaryContainer = OnGreenContainer,
  onPrimaryContainer = GreenContainer,
  secondary = GreenPrimary,
  onSecondary = PureWhite,
  background = Color(0xFF0F172A),
  onBackground = PureWhite,
  surface = Color(0xFF1E293B),
  onSurface = PureWhite,
  surfaceVariant = Color(0xFF334155),
  onSurfaceVariant = Color(0xFFCBD5E1),
  outline = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep clean white with green primary brand
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

