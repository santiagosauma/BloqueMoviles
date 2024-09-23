package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.HeaderSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionContent
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaDetalleItemTemplate


@Composable
fun ServiciosInfo(navController: NavController?) {
    PantallaDetalleItemTemplate(
        navController = navController,
        bottomBar = {
            BarraNav(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E))
            )
        },
        content = {

            HeaderSection("Título del Servicio", navController)
            SpacedItem(spacing = 16) {
                SectionTitle("Descripción")
            }
            SpacedItem(spacing = 8) {
                SectionContent("Descripción del servicio seleccionado.")
            }

            //FUNCIONES PARA EL PROCESAMIENTO MARKDOWN DEL CONTENIDO
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ServiciosInfoPreview() {
    ServiciosInfo(navController = null)
}
