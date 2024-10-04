package com.leotesta017.clinicapenal.model.modelUsuario

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val tipo: String ="",

    //INFORMACION ASOCIADA A CADA USUARIO
    val listAppointments: List<String> = emptyList(),
    val listComments: List<String> = emptyList(),
    val listCases: List<String> = emptyList(),
    val listExtraInfo: List<String> = emptyList()
)