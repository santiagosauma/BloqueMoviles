package com.leotesta017.clinicapenal.model.modelUsuario

import com.google.firebase.Timestamp

data class Appointment(
    val appointment_id: String = "",
    val fecha: Timestamp,
    val is_available: Boolean = false,
    val is_completed: Boolean = false,
    val is_suspended: Boolean = false,
    val valoration: Int = 0
)