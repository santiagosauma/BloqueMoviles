package com.leotesta017.clinicapenal.model

import androidx.compose.ui.graphics.Color

data class Casos_Representacion(
    val id: String,
    val tipo: String,
    val usuarioAsignado: String,
    val fechaRealizada: String,
    val estado: String,
    val estadoColor: Color
)
