package gov.anzong.androidnga.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import sp.phone.theme.ThemeManager

private val DarkColorPalette = lightColors(
    primary = PrimaryNight,
    primaryVariant = PrimaryGreen,
    secondary = PrimaryNight,
    background = Color(0XFF080C10)
)

private val GreenLightColorPalette = lightColors(
    primary = PrimaryGreen,
    primaryVariant = PrimaryGreen,
    secondary = PrimaryGreen,
    background = Color(0xFFFFF8E7)
)

private val BlackLightColorPalette = lightColors(
    primary = PrimaryBlack,
    primaryVariant = PrimaryBlack,
    secondary = PrimaryBlack,
    background = Color(0xFFFFF8E7)
)

private val BrownLightColorPalette = lightColors(
    primary = PrimaryBrown,
    primaryVariant = PrimaryBrown,
    secondary = PrimaryBrown,
    background = Color(0xFFFFF8E7)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme || ThemeManager.getInstance().isNightMode) {
        DarkColorPalette
    } else {
        val theme = ThemeManager.getInstance().themeIndex;
        when (theme) {
            0 -> BrownLightColorPalette
            1 -> GreenLightColorPalette
            else -> BlackLightColorPalette
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}