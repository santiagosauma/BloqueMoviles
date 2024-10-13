@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.usuarioColaborador

import android.widget.CalendarView
import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.AppointmentViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun Agendar(navController: NavController?, caseId: String, appointmentViewModel: AppointmentViewModel = viewModel()) {
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
                    IconButton(onClick = { navController?.popBackStack() }) {
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
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarFechaYHora(
    context: Context,
    selectedTime: String,
    availableHours: List<String>,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }
    var lastValidDate by remember { mutableStateOf("") }  // Para guardar la última fecha válida
    var expanded by remember { mutableStateOf(false) }
    var selectedTimeInternal by remember { mutableStateOf("") }  // Hora seleccionada interna

    // Componente de calendario
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Mostrar la fecha seleccionada
        if (selectedDate.isNotEmpty()) {
            Text(
                text = "Fecha seleccionada: $selectedDate",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF002366))
                    .padding(16.dp),
                color = Color.White,  // Letra blanca
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Implementación de AndroidView con CalendarView
        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    val today = Calendar.getInstance().timeInMillis
                    minDate = today
                    focusedMonthDateColor = 200 // Color azul oscuro para fechas del mes actual
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val calendar = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }

                        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                            // Mostrar mensaje y revertir a la última fecha válida
                            Toast.makeText(context, "No se pueden seleccionar fines de semana", Toast.LENGTH_SHORT).show()

                            if (lastValidDate.isNotEmpty()) {
                                val parts = lastValidDate.split("/")
                                if (parts.size == 3) {
                                    val lastYear = parts[2].toInt()
                                    val lastMonth = parts[1].toInt() - 1
                                    val lastDay = parts[0].toInt()

                                    this.date = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, lastYear)
                                        set(Calendar.MONTH, lastMonth)
                                        set(Calendar.DAY_OF_MONTH, lastDay)
                                    }.timeInMillis
                                }
                            }
                        } else {
                            val formattedDate = "$dayOfMonth/${month + 1}/$year"
                            selectedDate = formattedDate
                            lastValidDate = formattedDate

                            selectedTimeInternal = ""
                            onDateSelected(formattedDate)
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Dropdown de horas disponible una vez seleccionada la fecha
        if (selectedDate.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedTimeInternal,  // Mostrar la hora interna seleccionada
                    onValueChange = { },
                    label = { Text("Seleccionar Hora") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown arrow",
                            modifier = Modifier.clickable { expanded = !expanded }
                        )
                    },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF2F2F2),
                        focusedIndicatorColor = Color.Blue,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.Transparent
                    )
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    if (availableHours.isEmpty()) {
                        DropdownMenuItem(
                            text = {
                                Text("No hay horas disponibles", color = Color.Gray)
                            },
                            onClick = {
                                expanded = false
                            }
                        )
                    } else {
                        availableHours.forEach { hora ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedTimeInternal = hora
                                    onTimeSelected(hora)
                                    expanded = false
                                },
                                text = {
                                    Text(text = hora, color = Color.Black)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonAgendarCita(
    selectedDate: String,
    selectedTime: String,
    caseId: String,
    appointmentViewModel: AppointmentViewModel = viewModel(),
    caseViewModel: CaseViewModel = viewModel()
) {
    val context = LocalContext.current
    val lastAppointment by caseViewModel.lastAppointment.collectAsState()
    val error by caseViewModel.error.collectAsState()

    // Ejecutar la lógica para obtener la última cita usando LaunchedEffect
    LaunchedEffect(caseId) {
        caseViewModel.resetLastAppointment()
        caseViewModel.fetchLastAppointment(caseId)
    }

    Button(
        onClick = {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {

                val currentTimestamp = System.currentTimeMillis()

                val appointmentTimestamp = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse("$selectedDate $selectedTime")

                val canCreateNewAppointment = lastAppointment?.let { appointment ->
                    val lastAppointmentTimestamp = appointment.fecha.toDate().time
                    val isSuspended = appointment.suspended


                    isSuspended || lastAppointmentTimestamp < currentTimestamp
                } ?: true

                if (canCreateNewAppointment) {
                    val newAppointment = appointmentTimestamp?.let { Timestamp(it) }?.let {
                        Appointment(
                            appointment_id = "",
                            fecha = it,
                            asisted = false,
                            confirmed = false,
                            active = true,
                            suspended = false,
                            valoration = 0
                        )
                    }

                    newAppointment?.let { appointmentViewModel.addAppointmentToExistingCase(it, caseId) }
                    Toast.makeText(context, "Cita agendada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No se puede agendar una cita hasta que la última cita haya pasado o esté suspendida.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Selecciona una fecha y una hora", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
    ) {
        Text(
            text = "Agendar Cita",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AgendarPreview() {
    ClinicaPenalTheme {
        Agendar(navController = rememberNavController(),"1")
    }
}
