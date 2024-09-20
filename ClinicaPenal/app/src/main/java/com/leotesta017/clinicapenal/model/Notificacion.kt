package com.leotesta017.clinicapenal.model

data class Notificacion(
    val fecha: String = "",
    val titulo: String = "",
    val detalle: String = "",
    val enlace: String? = null, // Enlace opcional
    val rutaEnlace: String? = null, // Ruta opcional para navegar si tiene enlace
    val isImportant: Boolean = false // Para definir si tiene borde azul
)
