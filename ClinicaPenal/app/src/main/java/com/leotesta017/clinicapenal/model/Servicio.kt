package com.leotesta017.clinicapenal.model

data class Servicio(
    val id: String = "", // ID del documento en Firestore
    val titulo: String = "",
    val descripcion: String = "",
    val url_imagen: String = "",
    val ejemplos: List<Ejemplo> = emptyList(), // Lista de ejemplos asociados
    val recursos: List<Recursos> = emptyList() // Lista de recursos asociados
)
