package com.leotesta017.clinicapenal.model

data class Ejemplo(
    val id: String = "", // ID del documento en Firestore (si es necesario)
    val asociacionId: String = "", // ID del documento de la categoría o servicio a la que pertenece
    val descripcion: String = "" // Descripción del ejemplo
)
