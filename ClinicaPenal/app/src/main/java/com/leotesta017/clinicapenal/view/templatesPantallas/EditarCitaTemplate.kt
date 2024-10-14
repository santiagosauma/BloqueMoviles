@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.templatesPantallas

import android.content.Context
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.AppointmentViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun EditarCitaTemplate(
    navController: NavController?,
    appointmentId: String,
    popbackRoute: String,
    isUsuarioGeneral: Boolean,
    barraNav: @Composable () -> Unit,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {

    val appointment by appointmentViewModel.appointment.collectAsState()

    val isLoading = appointment == null

// Verificamos si el appointment está disponible antes de inicializar los estados
    var isCancelled by remember { mutableStateOf(appointment?.suspended ?: false) }
    var isConfirmed by remember { mutableStateOf(appointment?.confirmed ?: false) }
    var isAsistido by remember { mutableStateOf(appointment?.asisted ?: false) }

// Agregamos logs para ver si el appointment está disponible
    LaunchedEffect(appointment) {

        // Actualizamos los estados si el appointment no es null
        appointment?.let {
            Log.d("DEBUG", "Updating states with appointment values")
            isCancelled = it.suspended
            isConfirmed = it.confirmed
            isAsistido = it.asisted

        }
    }

    LaunchedEffect(Unit) {
        appointmentViewModel.fetchAppointment(appointmentId)
    }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var showReagendar by remember { mutableStateOf(false) }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
    else {
        Scaffold(
            topBar = { AgendarTopBar(navController, popbackRoute) },
            bottomBar = { barraNav() },
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))



                        BotonesEstadoCita(
                                isUsuarioGeneral = isUsuarioGeneral,
                                appointment = appointment ?: Appointment(),
                                initialVCancel = isCancelled,
                                initialVConfirmed = isConfirmed,
                                initialVAsistido = isAsistido,
                                onCancelarClick = { newisCancelled ->
                                    isCancelled = newisCancelled
                                },
                                onConfirmarClick = { newisConfirmed ->
                                    isConfirmed = newisConfirmed
                                },
                                onAsistioClick = { newisAsistido ->
                                    isAsistido = newisAsistido
                                }
                        )



                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón de Reagendar, con el selector de fecha y hora
                        BotonReagendar(
                            showReagendar = showReagendar,
                            onReagendarClick = { showReagendar = !showReagendar },
                            onDateSelected = { newDate ->
                                selectedDate = newDate
                            },
                            onTimeSelected = { newTime ->
                                selectedTime = newTime
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))


                        BotonGuardarCambios(
                            appointmentId,
                            selectedTime = selectedTime,
                            selectedDate = selectedDate,
                            isAsistido = isAsistido,
                            isConfirmed = isConfirmed,
                            isCancelled = isCancelled
                        )

                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        )
    }
}


@Composable
fun BotonReagendar(
    showReagendar: Boolean,
    onReagendarClick: () -> Unit,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    Column {
        // Botón de Reagendar
        OutlinedButton(
            onClick = { onReagendarClick() },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp),
            border = ButtonDefaults.outlinedButtonBorder,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text("Reagendar Cita", fontWeight = FontWeight.Bold)
        }
        if (showReagendar) {

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

            Spacer(modifier = Modifier.height(16.dp))

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
            onDateSelected(selectedDate)
            onTimeSelected(selectedTime)
        }else{
            val selectedDate by remember { mutableStateOf("") }
            val selectedTime by remember { mutableStateOf("") }

            onDateSelected(selectedDate)
            onTimeSelected(selectedTime)
        }
    }
}


@Composable
fun AgendarTopBar(navController: NavController?,route:String) {
    Column {
        TopBar()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 3.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = { navController?.navigate(route) }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Confirmar, Cancelar o Reagendar Cita",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BotonesEstadoCita(
    appointment: Appointment,
    isUsuarioGeneral: Boolean,
    initialVCancel: Boolean,
    initialVAsistido: Boolean,
    initialVConfirmed: Boolean,
    onCancelarClick: (Boolean) -> Unit,
    onConfirmarClick: (Boolean) -> Unit,
    onAsistioClick: (Boolean) -> Unit
) {
    var isCancelled by remember { mutableStateOf(initialVCancel) }
    var isConfirmed by remember { mutableStateOf(initialVConfirmed) }
    var isAsistido by remember { mutableStateOf(initialVAsistido) }

    LaunchedEffect(initialVCancel, initialVConfirmed, initialVAsistido) {
        isCancelled = initialVCancel
        isConfirmed = initialVConfirmed
        isAsistido = initialVAsistido
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(top = 18.dp)
        ) {
            // Botón para Cancelar Cita
            OutlinedButton(
                onClick = {
                    isCancelled = true
                    isConfirmed = false
                    onCancelarClick(isCancelled)
                    onConfirmarClick(isConfirmed)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isCancelled) Color(0xFF002366) else Color.White,
                    contentColor = if (isCancelled) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 24.dp, bottomStart = 24.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text(if (isCancelled) "Cancelado" else "Cancelar Cita", fontWeight = FontWeight.Bold)
            }

            val currentTimestamp = System.currentTimeMillis()
            val appointmentTimestamp = appointment.fecha.toDate().time

            // Botón para Confirmar Cita
            OutlinedButton(
                onClick = {
                    isConfirmed = true
                    isCancelled = false
                    onConfirmarClick(isConfirmed)
                    onCancelarClick(isCancelled)
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isConfirmed) Color(0xFF002366) else Color.White,
                    contentColor = if (isConfirmed) Color.White else Color.Black
                ),
                shape = if (isUsuarioGeneral || appointmentTimestamp > currentTimestamp)
                    RoundedCornerShape(
                        topStart = 0.dp, bottomStart = 0.dp,
                        topEnd = 24.dp, bottomEnd = 24.dp
                    )
                else RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text(if (isConfirmed) "Confirmado" else "Confirmar Cita", fontWeight = FontWeight.Bold)
            }

            // Botón para Asistió, solo visible si la cita ya ha pasado y no es un usuario general
            if (!isUsuarioGeneral && appointmentTimestamp < currentTimestamp) {
                OutlinedButton(
                    onClick = {
                        isAsistido = !isAsistido
                        onAsistioClick(isAsistido) // Notificar el cambio
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isAsistido) Color(0xFF002366) else Color.White,
                        contentColor = if (isAsistido) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(
                        topStart = 0.dp, bottomStart = 0.dp,
                        topEnd = 24.dp, bottomEnd = 24.dp
                    ),
                    border = ButtonDefaults.outlinedButtonBorder,
                    modifier = Modifier
                        .height(48.dp)
                ) {
                    Text("Asistió", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
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
fun BotonGuardarCambios(appointmentId: String,
                        selectedTime: String,
                        selectedDate:String,
                        isAsistido: Boolean,
                        isConfirmed: Boolean,
                        isCancelled: Boolean,
) {

    val appointmentViewModel: AppointmentViewModel = viewModel()
    val context = LocalContext.current
    Button(
        onClick = {
            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {

                val appointmentTimestamp = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
                ).parse("$selectedDate $selectedTime")


                val updateAppointmentData = mutableMapOf<String, Any>(
                    "asisted" to isAsistido,
                    "confirmed" to isConfirmed,
                    "suspended" to isCancelled,
                )

                appointmentTimestamp?.let {
                    updateAppointmentData["fecha"] = Timestamp(it)
                }
                appointmentViewModel.updateAppointment(appointmentId,updateAppointmentData)
                Toast.makeText(context,"Cita Actualizada con excito", Toast.LENGTH_LONG).show()

            }
            else if((selectedDate.isNotEmpty() && selectedTime.isEmpty())
                || selectedDate.isEmpty() && selectedTime.isNotEmpty())
            {
                Toast.makeText(context, "Selecciona una fecha y una hora validas", Toast.LENGTH_SHORT).show()
                return@Button
            }
            else{
                val updateAppointmentData = mutableMapOf<String, Any>(
                    "asisted" to isAsistido,
                    "confirmed" to isConfirmed,
                    "suspended" to isCancelled
                )
                appointmentViewModel.updateAppointment(appointmentId,updateAppointmentData)
                Toast.makeText(context,"Cita Actualizada con excito", Toast.LENGTH_LONG).show()

            }

        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
    ) {
        Text(text = "Guardar Cambios", color = Color.White)
    }
}