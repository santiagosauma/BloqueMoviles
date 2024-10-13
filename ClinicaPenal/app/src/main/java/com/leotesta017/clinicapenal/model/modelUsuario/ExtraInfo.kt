package com.leotesta017.clinicapenal.model.modelUsuario

import com.google.firebase.firestore.GeoPoint

data class ExtraInfo(
    val extraInfo_id: String = "",  // ID único para el extra info

    val id_Usuario: String = "",
    val victima: Boolean = false,
    val investigado: Boolean = false,
    val lugarProcedencia: String = "",
    val fiscalia: String = "",
    val crime: String = "",
    val ine: String = "",

    val nuc: String = "", //Numero unico de causa
    val carpetaJudicial: String = "",
    val carpetaInvestigacion: String = "",
    val afv: String = "", //Acceso a Fiscalia Virtual
    val passwordFV: String = "", // Contraseña a Fiscalia Virtual
    val fiscalTitular: String = "",
    val unidadInvestigacion: String = "",
    val direccionUI: String = "", //Direccion de la unidad de investigacion
)
