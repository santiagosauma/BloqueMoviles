package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.EditarCitaTemplate


@Composable
fun EditarCitaEstudiante(navController: NavController?, appointmentId: String, caseId: String)
{
    EditarCitaTemplate(
        navController = navController,
        appointmentId = appointmentId,
        popbackRoute = "detallecasoestudiante/$caseId" ,
        isUsuarioGeneral = false,
        caseId = caseId,
        barraNav = {
            EstudiantesBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    )
}