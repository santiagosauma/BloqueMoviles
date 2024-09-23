package com.leotesta017.clinicapenal.model

data class Servicio(
    val id: String = "", // ID del documento en Firestore
    val titulo: String = "",
    val descripcion: String = "",
    val url_imagen: String = "",
    val contenido: String = ""
)
