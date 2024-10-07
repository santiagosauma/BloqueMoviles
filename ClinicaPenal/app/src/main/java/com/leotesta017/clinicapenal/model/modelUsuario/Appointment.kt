package com.leotesta017.clinicapenal.model.modelUsuario

import com.google.firebase.Timestamp

data class Appointment(
    val appointment_id: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val asisted: Boolean = false,
    val confirmed: Boolean = false,
    val valoration: Int = 0
)