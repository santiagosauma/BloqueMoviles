package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.*
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoTemplate
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.model.Evento
import com.leotesta017.clinicapenal.repository.EventRepository
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PantallaInfoClinicaAdmin(navController: NavController?) {
    val eventRepository = EventRepository()
    var eventosState by remember { mutableStateOf<List<Evento>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        try {
            val eventos = eventRepository.getEventos()
            eventosState = eventos.sortedBy { it.fecha }
        } catch (e: Exception) {
            errorMessage = "Error al cargar eventos"
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar()
        },
        bottomBar = {
            AdminBarraNav(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ) { data ->
                Snackbar(
                    backgroundColor = Color(0xFF303665),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    eventosState == null && errorMessage == null -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    errorMessage != null -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = errorMessage!!, color = Color.Red)
                        }
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState()) // Habilita el scroll vertical
                        ) {
                            Calendarios(
                                title = "Calendario Eventos",
                                eventos = eventosState ?: emptyList(),
                                isCollaborator = true,
                                onDeleteEvento = { evento ->
                                    coroutineScope.launch {
                                        try {
                                            eventRepository.deleteEvento(evento.id)
                                            eventosState = eventRepository.getEventos().sortedBy { it.fecha }
                                            scaffoldState.snackbarHostState.showSnackbar("Evento eliminado con éxito.")
                                        } catch (e: Exception) {
                                            scaffoldState.snackbarHostState.showSnackbar("Error al eliminar el evento.")
                                        }
                                    }
                                }
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
                    }
                }
            }
        })
}