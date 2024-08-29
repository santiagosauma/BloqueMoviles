package com.leotesta017.bufetecapp.usuarioGeneral


import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
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
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.TopBar
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.DropdownTextField
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.TimeDropdownTextField


@Composable
fun Solicitud(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TopBar()
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

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SeleccionarLugar() {
    var selectedPlace by remember { mutableStateOf("") }
    val places = listOf("Lugar 1", "Lugar 2", "Lugar 3")

    DropdownTextField(
        label = "Lugar",
        options = places,
        selectedOption = selectedPlace,
        onOptionSelected = { selectedPlace = it }
    )
}



@Composable
fun SeleccionarProblema() {
    var selectedProblem by remember { mutableStateOf("") }
    val problems = listOf("Problema 1", "Problema 2", "Problema 3")

    DropdownTextField(
        label = "Problema",
        options = problems,
        selectedOption = selectedProblem,
        onOptionSelected = { selectedProblem = it }
    )
}

@Composable
fun SeleccionarFecha() {
    var selectedDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .width(350.dp)
            .padding(16.dp)
    ) {
        Text(
            text = if (selectedDate.isEmpty()) "Seleccionar Fecha" else "Fecha seleccionada: $selectedDate",
            style = MaterialTheme.typography.h6,
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

    TimeDropdownTextField(
        label = "Hora",
        options = horas,
        selectedTime = selectedTime,
        onTimeSelected = { selectedTime = it }
    )
}


@Composable
fun CheckboxConInformacion() {
    var isChecked by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(350.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text("Estoy enterado que en esta solicitud de servicio " +
                    "legal contará con participación de estudiantes")
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
            .padding(horizontal = 16.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F8C)) // Cambia el color de fondo del botón
    ) {
        Text(
            text = "Confirmar Cita",
            color = Color.White,
            style = MaterialTheme.typography.button.copy(fontSize = 18.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SolicitudPreview() {
    Solicitud(navController = null)
}