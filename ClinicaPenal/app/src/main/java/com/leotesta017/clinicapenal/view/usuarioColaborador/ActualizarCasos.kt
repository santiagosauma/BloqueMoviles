package com.leotesta017.clinicapenal.view.usuarioColaborador

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.templatesPantallas.BotonesEstado
import com.leotesta017.clinicapenal.view.templatesPantallas.BotonesRepresentacion
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaTemplateDetalleVistaCaso
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel

@Composable
fun ActualizarCasos(
    navController: NavController?,
    case_id: String,
    caseViewModel: CaseViewModel = viewModel()
)
{

    var representacionSeleccionada by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf("") }

    val context = LocalContext.current

    PantallaTemplateDetalleVistaCaso(
        navController = navController,
        caseId = case_id,
        routeComentario = "comentar",
        routeAgendar = "agendar",
        route = "generalsolicitudadmin",
        barraNav = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()

                )
            }

        },
        contenidoExtra = { case, abogadoseleccionado, estudianteseleccionado ->
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle("Representación")

            BotonesRepresentacion(caso = case, onRepresented = { isRepresented ->
                representacionSeleccionada = isRepresented  // Actualizar el valor seleccionado para representacion
            })

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Estado")

            BotonesEstado(caso = case, onEstadoChange = { estado ->
                estadoSeleccionado = estado
            })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Validación: Si no hay abogado o estudiante seleccionado, mostrar un Toast
                    if (abogadoseleccionado.id.isEmpty()) {
                        Toast.makeText(context, "Seleccione un abogado", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (estudianteseleccionado.id.isEmpty()) {
                        Toast.makeText(context, "Seleccione un estudiante", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    caseViewModel.assignUserToCase(case.case_id, abogadoseleccionado.id, "lawyerAssigned")
                    caseViewModel.assignUserToCase(case.case_id, estudianteseleccionado.id, "studentAssigned")

                    Toast.makeText(context, "Abogado y estudiante asignados correctamente", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
            ) {
                Text("Reasignar", color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {

                    val caseUpdateData = mapOf(
                        "represented" to representacionSeleccionada,
                        "state" to estadoSeleccionado,
                        "suspended" to (estadoSeleccionado == "Suspendido"),
                        "available" to (estadoSeleccionado != "Finalizado"),
                        "completed" to (estadoSeleccionado == "Finalizado")
                    )

                    caseViewModel.updateCase(case.case_id, caseUpdateData)

                    Toast.makeText(context, "Caso guardado exitosamente", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
            ) {
                Text("Guardar", color = Color.White)
            }
        }
    )
}







@Preview(showBackground = true)
@Composable
fun PreviewActualizarCasos() {
    ActualizarCasos(navController = null, case_id = "1")
}
