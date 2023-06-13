package test.app.todocompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple700 = Color(0xFF512DA8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Teal200 = Color(0xFF80CBC4)

val LightGray = Color(0xFFCECDCD)
val MediumGray = Color(0xFF8B757C)
val DarkGray = Color(0xFF44353A)

val LowPriorityColor = Color(0xFF4DB6AC)
val MediumPriorityColor = Color(0xFFFFEB3B)
val HighPriorityColor = Color(0xFFF44336)
val NonePriorityColor = Color(0xFFFFFFFF)

val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else LightGray

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple40 else Color.Black

val ColorScheme.fabBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Teal200 else Purple700