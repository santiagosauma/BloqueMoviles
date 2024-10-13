package com.leotesta017.clinicapenal.view.usuarioGeneral

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.usuarioColaborador.SeleccionarFechaYHora
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.AppointmentViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ExtraInfoViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun Solicitud(navController: NavController?) {
    val appointmentViewModel: AppointmentViewModel = viewModel()
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
                        text = "Nueva solicitud de cita",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            BarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    var estadoCaso by remember { mutableStateOf("") }
                    var lugar by remember { mutableStateOf("") }
                    var checkboxState by remember { mutableStateOf(false) }
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

                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionCasoLegal(onSelectedOptionChange = { nuevoEstado ->
                        estadoCaso = nuevoEstado }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarLugarInput(onPlaceChange = { nuevoLugar ->
                        lugar = nuevoLugar }
                    )
                    Spacer(modifier = Modifier.height(20.dp))


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
                    CheckboxConInformacion(onCheckedChange = { nuevoEstadoCheckbox ->
                        checkboxState = nuevoEstadoCheckbox }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    // Botón de agendar cita, pasando todos los valores
                    UserIdData.userId?.let {
                        BotonAgendarCita(
                            estadoCaso = estadoCaso,
                            lugar = lugar,
                            selectedDate = selectedDate,
                            selectedTime = selectedTime,
                            checkboxState = checkboxState,
                            userId = it
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    )
}



@Composable
fun SeleccionCasoLegal(onSelectedOptionChange: (String) -> Unit) {
    var selectedOption by remember { mutableStateOf("Víctima") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Estado de Caso legal",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            OutlinedButton(
                onClick = {
                    selectedOption = "Víctima"
                    onSelectedOptionChange(selectedOption) // Pasar el valor actualizado
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Víctima") Color(0xFF0B1F8C) else Color.White,
                    contentColor = if (selectedOption == "Víctima") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 24.dp, bottomStart = 24.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("Víctima", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = {
                    selectedOption = "Investigado"
                    onSelectedOptionChange(selectedOption) // Pasar el valor actualizado
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Investigado") Color(0xFF0B1F8C) else Color.White,
                    contentColor = if (selectedOption == "Investigado") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 24.dp, bottomEnd = 24.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Text("Investigado", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarLugarInput(onPlaceChange: (String) -> Unit) {
    var selectedPlace by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedPlace,
            onValueChange = {
                selectedPlace = it
                onPlaceChange(selectedPlace) // Pasar el valor actualizado
            },
            label = { Text("Lugar de Procedencia") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.Blue
            )
        )
    }
}

@Composable
fun CheckboxConInformacion(onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckedChange(isChecked) // Pasar el valor actualizado
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF0B1F8C),
                    uncheckedColor = Color.Gray
                )
            )
            Text(
                text = "Estoy enterado que en esta solicitud de servicio legal contará con participación de estudiantes",
                color = Color.Black,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun BotonAgendarCita(
    estadoCaso: String,
    lugar: String,
    selectedDate: String,
    selectedTime: String,
    checkboxState: Boolean,
    appointmentViewModel: AppointmentViewModel = viewModel(),
    userId: String
) {
    val appointmentResult by appointmentViewModel.appointmentResult.collectAsState()
    val error by appointmentViewModel.error.collectAsState()
    val context = LocalContext.current
    Button(
        onClick = {
            // Verificar que todos los campos estén completos
            if (lugar.isNotEmpty() && estadoCaso.isNotEmpty() && checkboxState && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                appointmentViewModel.canCreateNewCase(userId) { canCreate ->
                    if (canCreate) {
                        // Crear el objeto Appointment
                        val appointment = Appointment(
                            appointment_id = "",
                            fecha = convertToTimestamp(selectedDate, selectedTime),
                            asisted = false,
                            confirmed = false,
                            suspended = false,
                            valoration = 0
                        )
                        val victima: Boolean
                        val investigado: Boolean
                        if (estadoCaso == "Victima") {
                            victima = true
                            investigado = false
                        } else {
                            victima = false
                            investigado = true
                        }
                        // Llamar al ViewModel para agendar la cita y crear un nuevo caso
                        appointmentViewModel.createAppointmentAndNewCase(
                            appointment = appointment,
                            userId = userId,
                            place = lugar,
                            lugarProcedencia = lugar,
                            victima = victima,
                            investigado = investigado
                        )
                        Toast.makeText(context, "Cita creada con exito, favor de contestar el segundo formulario", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(context, "Ya tienes un caso activo, no puedes crear uno nuevo", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                Toast.makeText(context, "Por favor, ingrese toda la información para continuar", Toast.LENGTH_LONG).show()
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

    // Manejo de resultados
    when {
        appointmentResult == true -> {
            // Mostrar mensaje de éxito
            Text(text = "Cita y caso creados exitosamente", color = Color.Green)
            appointmentViewModel.resetAppointmentResult() // Resetear el estado
        }
        error != null -> {
            // Mostrar mensaje de error
            Text(text = "Error: $error", color = Color.Red)
        }
    }
}

// Función auxiliar para convertir la fecha y la hora a un Timestamp (de Firestore)
fun convertToTimestamp(date: String, time: String): Timestamp {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dateTimeString = "$date $time"
    val date = formatter.parse(dateTimeString)
    return Timestamp(date)
}




@Preview(showBackground = true)
@Composable
fun SolicitudPreview() {
    ClinicaPenalTheme {
        Solicitud(navController = rememberNavController())
    }
}
