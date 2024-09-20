package com.leotesta017.clinicapenal.model

import androidx.compose.ui.graphics.Color

data class Solicitud_Estudiante(
    val id: String = "",
    val titulo: String = "",
    val nombreUsuario: String = "",
    val fechaRealizada: String = "",
    val estado: String = "",
    val estadoColor: Color = Color.Unspecified
)
