package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.AgregarInfoTemplate

@Composable
fun AgregarServiciosInfoEstudiante(navController: NavController?) {
    AgregarInfoTemplate(
        navController = navController,
        titulo = "Agregar Servicio",
        textDescripcion = "Inserte Información del servicio...",
        bottomBarContent = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        onAddClick = {
            // Aquí va tu lógica para añadir (POST a Firebase)
        },
        onCancelClick = {
            // Aquí va tu lógica para cancelar
        }
    )
}


