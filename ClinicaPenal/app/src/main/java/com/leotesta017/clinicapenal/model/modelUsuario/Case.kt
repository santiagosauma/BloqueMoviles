package com.leotesta017.clinicapenal.model.modelUsuario

data class Case (
    val case_id: String = "",  // ID del caso
    val is_represented: Boolean = false,
    val lawyerAssigned: String = "",  // ID del abogado asignado
    val place: String = "",
    val situation: String = "",
    val state: String = "",
    val studentAssigned: String = ""  // ID del estudiante asignado
)