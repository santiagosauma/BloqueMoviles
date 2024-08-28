package com.leotesta017.bufetecapp.usuarioGeneral

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.TopBar
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.DropdownTextField
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.TimePickerTextField
import java.util.Calendar


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

                // Llamada a las subfunciones
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
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(modifier = Modifier.width(350.dp)) {
        Text("Fecha", style = MaterialTheme.typography.h6)

        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = if (selectedDate.isEmpty()) "Seleccionar Fecha" else selectedDate,
                style = MaterialTheme.typography.body1
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                    showDatePicker = false
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).apply {
                show()
            }
        }
    }
}



@Composable
fun SeleccionarHora() {
    var selectedTime by remember { mutableStateOf("") }

    TimePickerTextField(
        label = "Hora",
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

    Button(onClick = {
        Toast.makeText(context, "Cita confirmada",
            Toast.LENGTH_SHORT).show()
    }) {
        Text("Confirmar Cita")
    }
}

@Preview(showBackground = true)
@Composable
fun SolicitudPreview() {
    Solicitud(navController = null)
}