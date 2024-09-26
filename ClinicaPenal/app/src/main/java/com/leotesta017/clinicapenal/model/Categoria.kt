package com.leotesta017.clinicapenal.model

data class Categoria(
    val id: String = "", // ID del documento en Firestore
    val titulo: String = "",
    val descripcion: String = "",
    val contenido: String = "",
    val url_imagen: String = "",
)
