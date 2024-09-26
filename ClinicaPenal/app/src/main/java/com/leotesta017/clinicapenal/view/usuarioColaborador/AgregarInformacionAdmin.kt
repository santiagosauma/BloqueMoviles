package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.AgregarInfoTemplate

@Composable
fun AgregarInfoAdmin(navController: NavController?) {
    AgregarInfoTemplate(
        navController = navController,
        titulo = "Agregar Información",
        textDescripcion = "Inserte información ...",
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


@Preview(showBackground = true)
@Composable
fun PreviewAgregarInfoAdmin() {
    AgregarInfoAdmin(navController = null)
}
