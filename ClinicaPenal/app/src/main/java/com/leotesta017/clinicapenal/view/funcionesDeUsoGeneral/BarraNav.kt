package com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun BarraNav(navController: NavController?, modifier: Modifier = Modifier) {
    GenericBarraNav(
        navController = navController,
        modifier = modifier.fillMaxWidth(),
        destinations = listOf("pantallainfocategoriasgeneral", "solicitud", "pantallainformacionclinica"),
        icons = listOf(Icons.Default.Menu, Icons.Default.Folder, Icons.Default.Info),
        texts = listOf("Inicio", "Solicitudes", "Información")
    )
}

@Preview
@Composable
fun PreviewBarraNav() {
    BarraNav(navController = null)
}
