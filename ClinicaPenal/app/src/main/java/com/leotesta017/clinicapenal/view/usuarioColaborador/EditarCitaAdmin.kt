package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.EditarCitaTemplate

@Composable
fun EditarCitaAdmin(navController: NavController?,appointmentId: String,caseId: String)
{
    EditarCitaTemplate(
        navController = navController,
        appointmentId = appointmentId,
        popbackRoute = "actualizarcasos/$caseId" ,
        isUsuarioGeneral = false,
        caseId = caseId,
        barraNav = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    )
}