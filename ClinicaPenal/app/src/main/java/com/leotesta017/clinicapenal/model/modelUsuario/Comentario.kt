package com.leotesta017.clinicapenal.model.modelUsuario

import com.google.firebase.Timestamp

data class Comentario(
    val comentario_id: String = "",  // ID único del comentario
    val contenido: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val madeBy: String = "",//ID del usuario que hizo el comentario
    val important: Boolean = false,  // Si es importante o no
    val representation: String = "" // Cualquier representación adicional

)