package com.leotesta017.clinicapenal.solicitud

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.model.Notificacion
import com.leotesta017.clinicapenal.model.SolicitudGeneral
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.NotificacionItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SolicitudGeneralItem
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme


@Composable
fun GeneralSolicitud(
    navController: NavController?
) {
    val solicitudes = listOf(
        SolicitudGeneral(
            id = "ID123",
            fechaRealizada = "19/06/2024 - Cita Realizada",
            proximaCita = "12/08/2024 - Próxima Cita (16:30)",
            estado = "Finalizado",
            estadoColor = Color.Green
        ),
        // Otras solicitudes...
    )

    val notificaciones = listOf(
        Notificacion(
            fecha = "01/09/24",
            titulo = "Finalizado: Caso ID123",
            detalle = "Puede dejar su valoración en el Caso en el Histórico de Solicitudes",
            isImportant = true
        ),
        // Otras notificaciones...
    )

    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        titulo1 = "Historial de Solicitudes",
        items1 = solicitudes,
        itemComposable1 = { solicitud ->
            SolicitudGeneralItem(
                solicitud = solicitud as SolicitudGeneral,
                navController = navController,
                valorarRoute = "ReviewComentarios"
            )
        },
        titulo2 = "Notificaciones",
        items2 = notificaciones,
        itemComposable2 = { notificacion ->
            NotificacionItem(
                notificacion = notificacion as Notificacion,
                navController = navController
            )
        },
        barraNavComposable = {
            Box(modifier = Modifier.fillMaxSize()) {
                BarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    )
}

    @Preview(showBackground = true)
@Composable
fun GeneralSolicitudPreview() {
    ClinicaPenalTheme {
        GeneralSolicitud(navController = rememberNavController())
    }
}
