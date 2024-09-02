package com.leotesta017.clinicapenal.usuarioGeneral

import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.DropdownTextField
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TimeDropdownTextField
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme

@Composable
fun Solicitud(navController: NavController?) {
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
                        text = "Crear solicitud",
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
                    SeleccionarProblema()
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarLugar()
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarFecha()
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarHora()
                    Spacer(modifier = Modifier.height(20.dp))
                    CheckboxConInformacion()
                    Spacer(modifier = Modifier.height(20.dp))
                    BotonConfirmarCita()
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    )
}

@Composable
fun SeleccionarLugar() {
    var selectedPlace by remember { mutableStateOf("") }
    val places = listOf("Lugar 1", "Lugar 2", "Lugar 3")

    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownTextField(
            label = "Lugar",
            options = places,
            selectedOption = selectedPlace,
            onOptionSelected = { selectedPlace = it }
        )
    }
}

@Composable
fun SeleccionarProblema() {
    var selectedProblem by remember { mutableStateOf("") }
    val problems = listOf("Problema 1", "Problema 2", "Problema 3")

    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownTextField(
            label = "Problema",
            options = problems,
            selectedOption = selectedProblem,
            onOptionSelected = { selectedProblem = it }
        )
    }
}

@Composable
fun SeleccionarFecha() {
    var selectedDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = if (selectedDate.isEmpty()) "Seleccionar Fecha" else "Fecha seleccionada: $selectedDate",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF0B1F8C)),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        selectedDate = "$dayOfMonth/${month + 1}/$year"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )
    }
}

@Composable
fun SeleccionarHora() {
    val horas = listOf(
        "08:00", "09:00", "10:00", "11:00", "12:00",
        "13:00", "14:00", "15:00", "16:00", "17:00"
    )
    var selectedTime by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        TimeDropdownTextField(
            label = "Hora",
            options = horas,
            selectedTime = selectedTime,
            onTimeSelected = { selectedTime = it }
        )
    }
}

@Composable
fun CheckboxConInformacion() {
    var isChecked by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it },
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
fun BotonConfirmarCita() {
    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "Cita confirmada", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F8C))
    ) {
        Text(
            text = "Confirmar Cita",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SolicitudPreview() {
    ClinicaPenalTheme {
        Solicitud(navController = rememberNavController())
    }
}
