package com.leotesta017.clinicapenal.usuarioColaborador

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
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar

data class Solicitud(
    val id: String,
    val titulo: String,
    val nombreUsuario: String,
    val fechaRealizada: String,
    val estado: String,
    val estadoColor: Color
)

@Composable
fun GeneralSolicitudAdmin(navController: NavController?) {
    val solicitudes = remember {
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
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Histórico de Solicitudes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                SearchBar(searchText = "")
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(solicitudes) { solicitud ->
                SolicitudItem(solicitud = solicitud, navController = navController)
                HorizontalDivider(color = Color.Gray)
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
            .background(Color.White)
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
                    Text(text = solicitud.nombreUsuario)
                    Text(text = solicitud.fechaRealizada)
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GeneralSolicitudAdminPreview() {
    ClinicaPenalTheme {
        GeneralSolicitudAdmin(navController = rememberNavController())
    }
}
