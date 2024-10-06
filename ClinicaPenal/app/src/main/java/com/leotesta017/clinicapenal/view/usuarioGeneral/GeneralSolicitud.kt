package com.leotesta017.clinicapenal.solicitud

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CaseItemTemplate
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel



@Composable
fun GeneralSolicitud(navController: NavController?) {
    val usuarioViewModel: UsuarioViewModel = viewModel()

    val userId = UserIdData.userId ?: return

    LaunchedEffect(userId) {
        usuarioViewModel.fetchUserCasesWithDetails(userId)
    }

    // Observamos el estado de los casos y mostramos la información

    val userCases by usuarioViewModel.userCases.collectAsState()

    // Mostramos un mensaje de error si existe
    val error by usuarioViewModel.error.collectAsState()



    // Llamamos la función template para generar la pantalla
    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        titulo1 = "Historial de Casos", // Cambio de título
        items1 = userCases,  // Lista completa de casos ordenados
        itemComposable1 = { caso ->
            CaseItemTemplate(
                case = caso,
                onDelete = { },
                confirmDeleteText = "¿Estás seguro de que deseas eliminar este caso?"
            )
        },
        titulo2 = "Notificaciones", // Notificaciones vacías por ahora
        items2 = emptyList(),  // Lista vacía de notificaciones
        itemComposable2 = { },
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
