package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.ModificarInfoTemplate

@Composable
fun ModificarServiciosInfoAdmin(
    navController: NavController?,
    id: String,
    titulo: String,
    descripcion: String
) {
    // Supongamos que obtenemos "nombre" basado en el `id`
    val nombre = "Nombre actual del servicio"

    ModificarInfoTemplate(
        navController = navController,
        titulo = titulo,
        initialName = nombre,
        initialDescription = descripcion,
        id = id,
        bottomBarContent = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        onSaveClick = {
            // Aquí va tu lógica para guardar los cambios (UPDATE en Firebase)
        },
        onCancelClick = {
            // Aquí va tu lógica para cancelar
        }
    )
}



@Preview(showBackground = true)
@Composable
fun PreviewModificarServiciosAdmin() {
    ModificarServiciosInfoAdmin(
        navController = null,
        id = "123",
        titulo = "Modificar Servicio",
        descripcion = "Descripción del servicio"
    )
}