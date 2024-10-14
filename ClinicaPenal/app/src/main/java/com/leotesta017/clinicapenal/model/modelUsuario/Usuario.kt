package com.leotesta017.clinicapenal.model.modelUsuario

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val tipo: String ="",

    //INFORMACION ASOCIADA A CADA USUARIO DE LOS CASOS
    val listCases: List<String> = emptyList(),
    val fcmTokens: List<String> = emptyList()
)