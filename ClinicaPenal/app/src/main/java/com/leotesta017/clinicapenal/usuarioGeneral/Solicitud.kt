package com.leotesta017.clinicapenal.usuarioGeneral

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
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
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionCasoLegal() // Botón de Víctima e Investigado
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarLugarInput() // Input de Lugar de Procedencia
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarFecha()
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarHora() // Ajuste en la selección de hora
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarHora() {
    val horas = listOf("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00")
    var selectedTime by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedTime,
            onValueChange = { },
            label = { Text("Hora") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true, // Hace que no se pueda escribir en el campo
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.Transparent // Quita el cursor
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            horas.forEach { hora ->
                DropdownMenuItem(
                    onClick = {
                        selectedTime = hora
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


@Composable
fun SeleccionCasoLegal() {
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
                onClick = { selectedOption = "Víctima" },
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
                onClick = { selectedOption = "Investigado" },
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
fun SeleccionarLugarInput() {
    var selectedPlace by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedPlace,
            onValueChange = { selectedPlace = it },
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
                CalendarView(ContextThemeWrapper(context, R.style.CustomCalendarView)).apply {
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
            text = "Generar Solicitud de Cita",
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
