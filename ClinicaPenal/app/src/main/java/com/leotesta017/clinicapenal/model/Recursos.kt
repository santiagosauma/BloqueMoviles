package com.leotesta017.clinicapenal.model

data class Recursos(
    val id: String = "", // ID del documento en Firestore (si es necesario)
    val asociacionId: String = "", // ID del documento de la categoría o servicio a la que pertenece
    val url_linkrecurso: String = "" // Descripción del ejemplo
)
