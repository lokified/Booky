package com.loki.booko.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.loki.booko.R


val Madurai  = FontFamily(
    Font(R.font.arima_madurai_bold, FontWeight.Bold),
    Font(R.font.arima_madurai_extrabold, FontWeight.ExtraBold),
    Font(R.font.arima_madurai_light, FontWeight.Light),
    Font(R.font.arima_madurai_medium, FontWeight.Medium),
    Font(R.font.arima_madurai_extralight, FontWeight.ExtraLight),
    Font(R.font.arima_madurai_regular, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Madurai,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)