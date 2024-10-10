package com.leotesta017.clinicapenal.model.modelUsuario

import com.google.firebase.firestore.GeoPoint

data class ExtraInfo(
    val extraInfo_id: String = "",  // ID único para el extra info
    val id_Usuario: String = "",

    val crime: String = "",
    val ine: String = "",

    val nuc: String = "", //Numero unico de causa
    val carpetaJudicial: String = "",
    val carpetaInvestigacion: String = "",
    val afv: String = "", //Acceso a Fiscalia Virtual
    val passwordFV: String = "", // Contraseña a Fiscalia Virtual
    val fiscalTitular: String = "",
    val unidadInvestigacion: String = "",
    val direccionUI: GeoPoint = GeoPoint(0.0, 0.0) , //Direccion de la unidad de investigacion
)
