package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.model.Evento
import com.leotesta017.clinicapenal.repository.EventRepository
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.*
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoTemplate

@Composable
fun PantallaInfoClinica(navController: NavController?) {
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
        // Show a loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        // Show an error message
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage!!)
        }
    } else {
        // Data is loaded, show the content
        PantallaInfoTemplate(
            navController = navController,
            topBar = { TopBar() },
            bottomBar = {
                Box(modifier = Modifier.fillMaxSize()) {
                    BarraNav(
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
                    icons = listOf(R.drawable.facebook, R.drawable.logox, R.drawable.youtube),
                    onIconClick = { /* Handle icon click */ }
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInfoClinicaPreview() {
    PantallaInfoClinica(navController = null)
}
