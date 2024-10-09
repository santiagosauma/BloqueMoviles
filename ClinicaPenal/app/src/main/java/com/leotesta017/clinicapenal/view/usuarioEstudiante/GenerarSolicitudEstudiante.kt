package com.leotesta017.clinicapenal.view.usuarioEstudiante


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController


@Composable
fun GenerarSolicitudEstudiante(navController: NavController?) {
    val userId = UserIdData.userId

    // Llamada a la template con los ViewModels y el userId
    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        userId = userId,
        isAdmin = false,
        barraNavComposable = {
            Box(modifier = Modifier.fillMaxSize()) {
                EstudiantesBarraNav(
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
fun PreviewGenerarSolicitudEstudiante() {
    ClinicaPenalTheme {
        GenerarSolicitudEstudiante(navController = rememberNavController())
    }
}
