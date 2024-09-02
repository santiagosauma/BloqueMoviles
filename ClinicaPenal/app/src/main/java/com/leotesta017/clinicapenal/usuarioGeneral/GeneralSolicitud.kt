package com.leotesta017.clinicapenal.solicitud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.usuarioGeneral.SearchBar

data class Solicitud(
    val id: String,
    val titulo: String,
    val fechaRealizada: String,
    val proximaCita: String,
    val estado: String,
    val estadoColor: Color
)

@Composable
fun GeneralSolicitud(navController: NavController?) {
    val solicitudes = remember {
        listOf(
            Solicitud(
                id = "ID123",
                titulo = "Asalto",
                fechaRealizada = "19/06/2024 - Cita Realizada",
                proximaCita = "12/08/2024 - Próxima Cita (16:30)",
                estado = "Finalizado",
                estadoColor = Color.Green
            ),
            Solicitud(
                id = "ID125",
                titulo = "Derecho Financiero",
                fechaRealizada = "20/06/2024 - Cita No Realizada",
                proximaCita = "",
                estado = "Suspendido",
                estadoColor = Color.Red
            ),
            Solicitud(
                id = "ID124",
                titulo = "Vandalismo",
                fechaRealizada = "28/07/2024 - Cita Re-Programada",
                proximaCita = "01/08/2024 - Próxima Cita (14:30)",
                estado = "Activo",
                estadoColor = Color.Yellow
            ),
            Solicitud(
                id = "ID126",
                titulo = "Conflicto Herencia",
                fechaRealizada = "06/08/2024 - Próxima Cita (9:00)",
                proximaCita = "06/08/2024 - Próxima Cita (9:00)",
                estado = "Activo",
                estadoColor = Color.Yellow
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)  // Fondo blanco para evitar problemas de renderizado
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.Start // Alineación a la izquierda
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Histórico de Solicitudes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar(searchText = "")
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(solicitudes) { solicitud ->
                SolicitudItem(solicitud = solicitud, navController = navController)
                Divider(color = Color.Gray)
            }
        }

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SolicitudItem(solicitud: Solicitud, navController: NavController?) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))  // Gris más claro
            .padding(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "${solicitud.id} - ${solicitud.titulo}", fontWeight = FontWeight.Bold)
                    Text(text = solicitud.fechaRealizada)
                    if (solicitud.proximaCita.isNotEmpty()) {
                        Text(
                            text = solicitud.proximaCita,
                            color = Color.Blue
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(text = solicitud.estado, color = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(solicitud.estadoColor, shape = MaterialTheme.shapes.small)
                        )
                    }
                    if (solicitud.estado == "Finalizado") {
                        IconButton(onClick = { expanded = !expanded }) { // Cambiado a toggle para menor recomposición
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Más opciones",
                                tint = Color.Black
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    navController?.navigate("ReviewComentarios")
                                },
                                text = { Text("Valorar") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeneralSolicitudPreview() {
    ClinicaPenalTheme {
        GeneralSolicitud(navController = rememberNavController())
    }
}
