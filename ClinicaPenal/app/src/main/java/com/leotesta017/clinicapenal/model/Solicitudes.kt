package com.leotesta017.clinicapenal.model

import androidx.compose.ui.graphics.Color

interface SolicitudBase {
    val id: String
    val fechaRealizada: String
    val estado: String
    val estadoColor: Color
}

data class SolicitudAdmin(
    override val id: String,
    val titulo: String,
    val nombreUsuario: String,
    override val fechaRealizada: String,
    override val estado: String,
    override val estadoColor: Color
) : SolicitudBase

data class SolicitudGeneral(
    override val id: String,
    override val fechaRealizada: String,
    val proximaCita: String,
    override val estado: String,
    override val estadoColor: Color
) : SolicitudBase

data class CasosRepresentacion(
    override val id: String,
    val tipo: String,
    val usuarioAsignado: String,
    override val fechaRealizada: String,
    override val estado: String,
    override val estadoColor: Color
) : SolicitudBase

data class Notificacion(
    val fecha: String = "",
    val titulo: String = "",
    val detalle: String = "",
    val enlace: String? = null, // Enlace opcional
    val rutaEnlace: String? = null, // Ruta opcional para navegar si tiene enlace
    val isImportant: Boolean = false // Para definir si tiene borde azul
)