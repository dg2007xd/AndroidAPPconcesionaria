package com.diegog.proyectoconcesionaria.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.diegog.proyectoconcesionaria.R

// Definición de la familia tipográfica SF Pro extraída de la plantilla de Behance
val sfProFamily = FontFamily(
    Font(R.font.sfpro_regular, FontWeight.Normal),
    Font(R.font.sfpro_medium, FontWeight.Medium),
    Font(R.font.sfpro_semibold, FontWeight.SemiBold),
    Font(R.font.sfpro_bold, FontWeight.Bold)
)

/*
 * Sistema tipográfico basado en SF Pro
 * Replicando la jerarquía limpia, moderna y altamente legible de la interfaz de lujo.
 */

val Typography = Typography(

    // -------------------------------------------------
    // DISPLAY
    // Para títulos colosales o pantallas de bienvenida / Onboarding
    // -------------------------------------------------
    displayLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),

    // -------------------------------------------------
    // HEADLINES
    // Títulos principales de pantallas (Ej. "Wishlist", "My Orders")
    // -------------------------------------------------
    headlineLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),


    // -------------------------------------------------
    // TITLES
    // Nombres de vehículos en tarjetas (Ej. "BMW 5 Series 2026", "Sporty Coupe")
    // -------------------------------------------------
    titleLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),

    titleSmall = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),


    // -------------------------------------------------
    // BODY
    // Descripciones, especificaciones del auto o subtítulos de ubicación
    // -------------------------------------------------
    bodyLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),

    bodySmall = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),


    // -------------------------------------------------
    // LABELS
    // Precios (Ej. "$200,000") y botones de acción ("Make an Offer")
    // -------------------------------------------------
    labelLarge = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    labelMedium = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

    labelSmall = TextStyle(
        fontFamily = sfProFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)