package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.AgregarInfoTemplate

@Composable
fun AgregarServiciosInfoEstudiante(navController: NavController?) {
    AgregarInfoTemplate(
        navController = navController,
        titulo = "Agregar Servicio",
        textDescripcion = "Inserte Información del servicio...",
        bottomBarContent = {
            EstudiantesBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        onAddClick = { n,c,d,u->
            // Aquí va tu lógica para añadir (POST a Firebase)
        },
        onCancelClick = {
            // Aquí va tu lógica para cancelar
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAgregarServiciosInfoEstudiante() {
    AgregarServiciosInfoEstudiante(navController = null)
}
