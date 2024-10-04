package com.leotesta017.clinicapenal.model.modelUsuario

data class ExtraInfo(
    val extraInfo_id: String = "",  // ID único para el extra info
    val crime: String = "", 
    val ine: String = "",
    val prosecutor: String = ""  // Fiscal o abogado relacionado
)
