package com.acadify.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.acadify.R

object Nunito {
    val regular = FontFamily(Font(R.font.nunito_regular, FontWeight.Normal))
    val semibold = FontFamily(Font(R.font.nunito_semibold, FontWeight.SemiBold))
    val bold = FontFamily(Font(R.font.nunito_bold, FontWeight.Bold))
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = Nunito.regular,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Nunito.bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito.semibold,
        fontSize = 15.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)