package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar

data class Solicitud(
    val id: String,
    val titulo: String,
    val nombreUsuario: String,
    val fechaRealizada: String,
    val estado: String,
    val estadoColor: Color
)

data class Casos_Representacion(
    val id: String,
    val tipo: String,
    val usuarioAsignado: String,
    val fechaRealizada: String,
    val estado: String,
    val estadoColor: Color
)

@Composable
fun GeneralSolicitudAdmin(navController: NavController?) {
    var solicitudes by remember {
        mutableStateOf(
            listOf(
                Solicitud(
                    id = "ID123",
                    titulo = "Asalto",
                    nombreUsuario = "Fernando Balleza",
                    fechaRealizada = "19/07/2024",
                    estado = "Finalizado",
                    estadoColor = Color.Green
                ),
                Solicitud(
                    id = "ID125",
                    titulo = "Violencia Familiar",
                    nombreUsuario = "Edsel Espidio",
                    fechaRealizada = "26/07/2024",
                    estado = "Suspendido",
                    estadoColor = Color.Red
                ),
                Solicitud(
                    id = "ID124",
                    titulo = "Vandalismo",
                    nombreUsuario = "Edsel Espidio",
                    fechaRealizada = "22/07/2024",
                    estado = "Activo",
                    estadoColor = Color.Yellow
                ),
                Solicitud(
                    id = "ID126",
                    titulo = "Conflicto Herencia",
                    nombreUsuario = "Abdiel Vaquera",
                    fechaRealizada = "01/08/2024",
                    estado = "Activo",
                    estadoColor = Color.Yellow
                )
            )
        )
    }

    var casos_representacion by remember {
        mutableStateOf(
            listOf(
                Casos_Representacion(
                    id = "ID123",
                    tipo = "Asalto",
                    usuarioAsignado = "Fernando Balleza",
                    fechaRealizada = "19/07/2024",
                    estado = "Finalizado",
                    estadoColor = Color.Green
                ),
                Casos_Representacion(
                    id = "ID125",
                    tipo = "Violencia Familiar",
                    usuarioAsignado = "Edsel Espidio",
                    fechaRealizada = "26/07/2024",
                    estado = "Suspendido",
                    estadoColor = Color.Red
                ),
                Casos_Representacion(
                    id = "ID124",
                    tipo = "Vandalismo",
                    usuarioAsignado = "Edsel Espidio",
                    fechaRealizada = "22/07/2024",
                    estado = "Activo",
                    estadoColor = Color.Yellow
                ),
                Casos_Representacion(
                    id = "ID126",
                    tipo = "Conflicto Herencia",
                    usuarioAsignado = "Abdiel Vaquera",
                    fechaRealizada = "01/08/2024",
                    estado = "Activo",
                    estadoColor = Color.Yellow
                )
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
                SearchBar(searchText = "")
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Citas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(solicitudes) { solicitud ->
                SolicitudItem(
                    solicitud = solicitud,
                    navController = navController,
                    onDelete = { id ->
                        solicitudes = solicitudes.filterNot { it.id == id }
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Casos Representacion",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(casos_representacion) { casos_representacion ->
                CasoRepresentacionItem(
                    casosRepresentacion = casos_representacion,
                    navController = navController,
                    onDelete = { id ->
                        solicitudes = solicitudes.filterNot { it.id == id }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AdminBarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SolicitudItem(
    solicitud: Solicitud,
    navController: NavController?,
    onDelete: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp)
            .clickable {
                navController?.navigate("actualizarcasos")
            },
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
                        text = "${solicitud.id} - ${solicitud.titulo}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    Text(
                        text = solicitud.fechaRealizada,
                        fontSize = 14.5.sp
                    )
                    Text(
                        text = solicitud.nombreUsuario,
                        fontSize = 14.5.sp
                    )
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
                                showDialog = true
                            },
                            text = { Text("Eliminar") }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(solicitud.id)
                    showDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Está seguro de que desea eliminar esta solicitud?") }
        )
    }
}

@Composable
fun CasoRepresentacionItem(
    casosRepresentacion: Casos_Representacion,
    navController: NavController?,
    onDelete: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp)
            .clickable {
                navController?.navigate("actualizarcasos")
            },
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
                        text = "Caso: ${casosRepresentacion.id} - ${casosRepresentacion.tipo}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    Text(
                        text = "Fecha: ${casosRepresentacion.fechaRealizada}",
                        fontSize = 14.5.sp
                    )
                    Text(
                        text = "Usuario Asignado: ${casosRepresentacion.usuarioAsignado}",
                        fontSize = 14.5.sp
                    )
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
                            text = casosRepresentacion.estado,
                            color = Color.Black,
                            fontSize = 14.5.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(casosRepresentacion.estadoColor, shape = CircleShape)
                        )
                    }
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
                                showDialog = true
                            },
                            text = { Text("Eliminar") }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(casosRepresentacion.id)
                    showDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Está seguro de que desea eliminar este caso de representación?") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GeneralSolicitudAdminPreview() {
    ClinicaPenalTheme {
        GeneralSolicitudAdmin(navController = rememberNavController())
    }
}
