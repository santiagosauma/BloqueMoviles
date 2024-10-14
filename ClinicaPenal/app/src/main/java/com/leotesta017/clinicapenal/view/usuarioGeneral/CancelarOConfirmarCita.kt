package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.EditarCitaTemplate

@Composable
fun CancelarOConfirmarCita(navController: NavController?, appointmentId: String)
{
    EditarCitaTemplate(
        navController = navController,
        appointmentId = appointmentId,
        popbackRoute = "solicitud" ,
        isUsuarioGeneral = true,
        barraNav = {
            BarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        }
    )
}