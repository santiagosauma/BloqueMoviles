package com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AdminBarraNav(navController: NavController?, modifier: Modifier = Modifier) {
    GenericBarraNav(
        navController = navController,
        modifier = modifier,
        destinations = listOf("pantallainfoadmin", "generalsolicitudadmin", "infoclinicaadmin"),
        icons = listOf(Icons.Default.Menu, Icons.Default.Folder, Icons.Default.Info),
        texts = listOf("Inicio", "Solicitudes", "Información")
    )
}



@Preview(showBackground = true)
@Composable
fun PreviewAdminBarraNav() {
    AdminBarraNav(navController = null)

}