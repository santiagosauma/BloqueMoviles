package com.leotesta017.clinicapenal.model

import java.util.Date

data class Evento(
    val id: String = "",
    val fecha: Date = Date(),
    val titulo: String = "",
    val lugar: String = ""
)