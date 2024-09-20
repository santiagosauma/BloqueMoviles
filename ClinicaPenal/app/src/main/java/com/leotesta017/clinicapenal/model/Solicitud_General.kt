package com.leotesta017.clinicapenal.model

import androidx.compose.ui.graphics.Color

data class Solicitud_General(
    val id: String = "",
    val fechaRealizada: String = "",
    val proximaCita: String = "",
    val estado: String = "",
    val estadoColor: Color = Color.Unspecified
)
