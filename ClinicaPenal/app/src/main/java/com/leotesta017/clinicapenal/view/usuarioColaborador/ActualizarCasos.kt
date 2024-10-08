package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.templatesPantallas.BotonesEstado
import com.leotesta017.clinicapenal.view.templatesPantallas.BotonesRepresentacion
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaTemplateDetalleVistaCaso

@Composable
fun ActualizarCasos(navController: NavController?, case_id: String) {
    PantallaTemplateDetalleVistaCaso(
        navController = navController,
        caseId = case_id,
        route = "route",
        barraNav = {
            Box(modifier = Modifier.fillMaxSize())
            {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }

        },
        contenidoExtra = {
            // Aquí puedes agregar contenido adicional específico para admin
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Representación")
            BotonesRepresentacion()

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Estado")
            BotonesEstado()

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Acción Reasignar */ },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Re-Asignar", color = Color.White)
            }
        }
    )
}







@Preview(showBackground = true)
@Composable
fun PreviewActualizarCasos() {
    ActualizarCasos(navController = null, case_id = "1")
}
