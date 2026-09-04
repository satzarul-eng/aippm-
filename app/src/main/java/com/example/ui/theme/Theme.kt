package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WarRoomColorScheme = darkColorScheme(
    primary = CrimsonPrimary,
    onPrimary = Color.White,
    primaryContainer = CrimsonDark,
    onPrimaryContainer = Color.White,
    secondary = GoldAmber,
    onSecondary = Color.Black,
    secondaryContainer = GoldDark,
    onSecondaryContainer = GoldLight,
    tertiary = BlueAuthority,
    onTertiary = Color.White,
    background = WarRoomBlack,
    onBackground = TextWhite,
    surface = WarRoomCharcoal,
    onSurface = TextWhite,
    surfaceVariant = WarRoomSurface,
    onSurfaceVariant = TextMuted,
    outline = WarRoomBorder,
    outlineVariant = WarRoomBorderGlow
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = WarRoomColorScheme,
        typography = Typography,
        content = content
    )
}
