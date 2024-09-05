package com.leotesta017.clinicapenal.solicitud

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme

data class Solicitud(
    val id: String,
    val fechaRealizada: String,
    val proximaCita: String,
    val estado: String,
    val estadoColor: Color
)

data class Notificacion(
    val fecha: String,
    val titulo: String,
    val detalle: String,
    val enlace: String? = null, // Enlace opcional
    val rutaEnlace: String? = null, // Ruta opcional para navegar si tiene enlace
    val isImportant: Boolean = false // Para definir si tiene borde azul
)


@Composable
fun GeneralSolicitud(navController: NavController?) {
    val solicitudes = remember {
        listOf(
            Solicitud(
                id = "ID123",
                fechaRealizada = "19/06/2024 - Cita Realizada",
                proximaCita = "12/08/2024 - Próxima Cita (16:30)",
                estado = "Finalizado",
                estadoColor = Color.Green
            ),
            Solicitud(
                id = "ID125",
                fechaRealizada = "20/06/2024 - Cita No Realizada",
                proximaCita = "01/08/2024 - Representación Rechazada",
                estado = "Suspendido",
                estadoColor = Color.Red
            ),
            Solicitud(
                id = "ID124",
                fechaRealizada = "28/07/2024 - Cita Re-Programada",
                proximaCita = "01/08/2024 - Próxima Cita (14:30)",
                estado = "Activo",
                estadoColor = Color.Yellow
            ),
            Solicitud(
                id = "ID126",
                fechaRealizada = "06/08/2024 - Próxima Cita (9:00)",
                proximaCita = "06/08/2024 - Próxima Cita (9:00)",
                estado = "Activo",
                estadoColor = Color.Yellow
            )
        )
    }

    val notificaciones = remember {
        listOf(
            Notificacion(
                fecha = "01/09/24",
                titulo = "Finalizado: Caso ID123",
                detalle = "Puede dejar su valoración en el Caso en el Histórico de Solicitudes",
                isImportant = true
            ),
            Notificacion(
                fecha = "12/08/24",
                titulo = "Representación Confirmada: Caso ID125",
                detalle = "Llené el formulario que se encuentra en las opciones del caso (Historial de Solicitudes)"
            ),
            Notificacion(
                fecha = "08/08/24",
                titulo = "Agenda Actualizada: Caso ID123",
                detalle = "Cita Asignada:",
                enlace = "18/02/25, 17:30",
                rutaEnlace = "detalle_caso/ID123"
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Historial de Solicitudes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

            }

            items(solicitudes) { solicitud ->
                SolicitudItem(solicitud = solicitud, navController = navController)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Notificaciones",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            items(notificaciones) { notificacion ->
                NotificacionItem(notificacion = notificacion, navController = navController)
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Caso" + " ${solicitud.id}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    Text(
                        text = solicitud.fechaRealizada,
                        fontSize = 14.5.sp
                    )
                    if (solicitud.proximaCita.isNotEmpty() && solicitud.estado != "Suspendido") {
                        Text(
                            text = solicitud.proximaCita,
                            color = Color.Blue,
                            fontSize = 14.5.sp
                        )
                    }
                    if (solicitud.proximaCita.isNotEmpty() && solicitud.estado == "Suspendido") {
                        Text(
                            text = solicitud.proximaCita,
                            color = Color.Red,
                            fontSize = 14.5.sp
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
                        Text(
                            text = solicitud.estado,
                            color = Color.Black,
                            fontSize = 14.5.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(solicitud.estadoColor, shape = CircleShape)
                        )
                    }
                    if (solicitud.estado == "Finalizado" ) {
                        IconButton(onClick = { expanded = !expanded }) {
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
                    if (solicitud.estado == "Activo" ) {
                        IconButton(onClick = { expanded = !expanded }) {
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
                                    navController?.navigate("SegundoFormulario")
                                },
                                text = { Text("Formulario") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificacionItem(
    notificacion: Notificacion,
    navController: NavController?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, if (notificacion.isImportant) Color.Blue else Color.Transparent, RoundedCornerShape(16.dp))
            .shadow(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icono de notificación",
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notificacion.fecha,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notificacion.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notificacion.detalle,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                if (notificacion.enlace != null) {
                    Text(
                        text = notificacion.enlace,
                        fontSize = 14.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable {
                            notificacion.rutaEnlace?.let { navController?.navigate(it) }
                        }
                    )
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
