// PantallaInfoClinicaAdmin.kt
package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.*
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoTemplate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.model.Evento
import com.leotesta017.clinicapenal.repository.EventRepository

@Composable
fun PantallaInfoClinicaAdmin(navController: NavController?) {
    val eventRepository = EventRepository()
    var eventosState by remember { mutableStateOf<List<Evento>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            eventosState = eventRepository.getEventos()
        } catch (e: Exception) {
            errorMessage = "Error al cargar eventos"
        }
    }

    if (eventosState == null && errorMessage == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage!!)
        }
    } else {
        PantallaInfoTemplate(
            navController = navController,
            topBar = { TopBar() },
            bottomBar = {
                Box(modifier = Modifier.fillMaxSize()) {
                    AdminBarraNav(
                        navController = navController,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                    )
                }
            },
            content = {
                Calendarios(
                    title = "Calendario Eventos",
                    eventos = eventosState ?: emptyList()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            navController?.navigate("AgregarEvento")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF002366))
                    ) {
                        Text(text = "Añadir", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                SeccionNosotros(
                    title = "Nosotros",
                    description = "Apoyamos a la comunidad con representación legal en procesos judiciales, asegurando que todos tengan acceso a la justicia."
                )
                Spacer(modifier = Modifier.height(20.dp))
                SeccionHorarios(
                    title = "Horarios",
                    schedule = "Lunes a Viernes de 9:00 AM a 4:00 PM"
                )
                Spacer(modifier = Modifier.height(20.dp))
                SeccionDirecciones(
                    title = "Direcciones",
                    addresses = listOf(
                        "Av. Eugenio Garza Sada 2501 Sur, Monterrey, N.L.",
                        "Rufino Tamayo y Eugenio, Av. Eugenio Garza Lagüera, San Pedro Garza García, N.L."
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                SeccionContacto(
                    title = "Contacto",
                    phone = "+52 0123456789",
                    email = "ClinicaPenal@tec.mx"
                )
                Spacer(modifier = Modifier.height(20.dp))
                RedesSociales(
                    title = "Redes Sociales",
                    icons = listOf(
                        R.drawable.facebook,
                        R.drawable.logox,
                        R.drawable.youtube
                    ),
                    onIconClick = { /* Manejar clic en el icono */ }
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInfoClinicaAdminPreview() {
    PantallaInfoClinicaAdmin(navController = null)
}
