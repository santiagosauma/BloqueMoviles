package com.leotesta017.clinicapenal.model.modelUsuario


data class Case (
    val case_id: String = "",
    val represented: Boolean = false,
    val available: Boolean = false,
    val completed: Boolean = false,
    val suspended: Boolean = false,
    val lawyerAssigned: String = "",
    val place: String = "",
    val situation: String = "",
    val state: String = "",
    val studentAssigned: String = "",
    val segundoFormulario: Boolean = false,
    val listAppointments: List<String> = emptyList(),
    val listExtraInfo: List<String> = emptyList(),
    val listComents: List<String> = emptyList()
)