package com.leotesta017.clinicapenal.view.usuarioColaborador

import android.view.ContextThemeWrapper
import android.widget.CalendarView
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme

@Composable
fun Agendar(navController: NavController?) {
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
                    SeleccionarFecha()
                    Spacer(modifier = Modifier.height(20.dp))
                    SeleccionarHoraDropdown()
                    Spacer(modifier = Modifier.height(20.dp))
                    BotonAgendarCita()
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarHoraDropdown() {
    val horas = listOf("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00")
    var selectedTime by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedTime,
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
fun BotonAgendarCita() {
    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "Cita agendada con éxito", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F8C))
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
        Agendar(navController = rememberNavController())
    }
}
