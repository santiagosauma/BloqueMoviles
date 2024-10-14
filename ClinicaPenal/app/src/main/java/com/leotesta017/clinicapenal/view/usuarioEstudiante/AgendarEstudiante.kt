package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.usuarioColaborador.BotonAgendarCita
import com.leotesta017.clinicapenal.view.usuarioColaborador.SeleccionarFechaYHora
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.AppointmentViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AgendarEstudiante(navController: NavController?,
                      caseId: String,
                      appointmentViewModel: AppointmentViewModel = viewModel())
{
    Scaffold(
        topBar = {
            Column {
                TopBar()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 3.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = { navController?.navigate("detallecasoestudiante/$caseId") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Agendar Cita",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            EstudiantesBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    var selectedDate by remember { mutableStateOf("") }
                    var selectedTime by remember { mutableStateOf("") }

                    var availableHours by remember { mutableStateOf(listOf<String>()) }
                    val appointmentsByDate by appointmentViewModel.appointmentsByDate.collectAsState()
                    val error by appointmentViewModel.error.collectAsState()

                    LaunchedEffect(selectedDate) {
                        availableHours = listOf()
                        appointmentViewModel.resetAppointments()
                        if (selectedDate.isNotEmpty()) {
                            appointmentViewModel.fetchAppointmentsByDate(selectedDate)
                        }
                    }

                    LaunchedEffect(appointmentsByDate, error) {
                        if (appointmentsByDate.isNotEmpty()) {
                            val bookedHours = appointmentsByDate.map { appointment ->
                                SimpleDateFormat("HH:mm", Locale.getDefault()).format(appointment.fecha.toDate())
                            }
                            val allHours = listOf("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
                            availableHours = allHours.filterNot { it in bookedHours }
                        } else if (error == "No hay citas para la fecha seleccionada") {
                            availableHours = listOf("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
                        } else if (!error.isNullOrEmpty()) {
                            availableHours = emptyList()
                        }
                    }

                    val context = LocalContext.current

                    SeleccionarFechaYHora(
                        context = context,
                        selectedTime = selectedTime,
                        availableHours = availableHours,
                        onDateSelected = { newDate ->
                            selectedDate = newDate
                            appointmentViewModel.fetchAppointmentsByDate(newDate)
                        },
                        onTimeSelected = { newTime ->
                            selectedTime = newTime
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    BotonAgendarCita(
                        caseId = caseId,
                        selectedDate = selectedDate,
                        selectedTime = selectedTime
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    )
}