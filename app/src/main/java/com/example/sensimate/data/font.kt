package com.example.sensimate.model

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.sensimate.R

/**
 * Creates a FontFamily with the Manrope font in various weights.
 * The weights included are Thin, Light, Normal, Medium, SemiBold, Bold, and ExtraBold.
 * @author Yusuf Kara
 */
val manropeFamily = FontFamily(
    Font(R.font.manrope_thin, FontWeight.Thin),
    Font(R.font.manrope_light, FontWeight.Light),
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold),
    Font(R.font.manrope_extrabold, FontWeight.ExtraBold),
)