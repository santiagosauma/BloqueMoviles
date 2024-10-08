package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaTemplateDetalleVistaCaso

@Composable
fun DetalleCasoEstudiante(
    navController: NavController?,
    case_id: String,
)
{
    PantallaTemplateDetalleVistaCaso(
        navController = navController,
        caseId = case_id,
        route = "route",
        barraNav = {
            Box(modifier = Modifier.fillMaxSize()){
                EstudiantesBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }

        },
        contenidoExtra = {
            // Aquí puedes agregar contenido adicional específico para estudiantes
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Acciones adicionales para estudiantes",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewDetalleCasoEstudiante() {
    DetalleCasoEstudiante(navController = null, case_id = "1")
}
