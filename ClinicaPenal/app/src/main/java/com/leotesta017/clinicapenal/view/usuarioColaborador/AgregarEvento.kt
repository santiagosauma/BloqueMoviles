package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.model.Evento
import com.leotesta017.clinicapenal.repository.EventRepository
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AgregarEvento(navController: NavController?) {
    var fechaText by remember { mutableStateOf(TextFieldValue("")) }
    var horaText by remember { mutableStateOf(TextFieldValue("")) }
    var titulo by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val eventRepository = EventRepository()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var shouldShowSuccess by remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(shouldShowSuccess) {
        if (shouldShowSuccess) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Evento guardado exitosamente",
                duration = SnackbarDuration.Short
            )
            delay(2000)
            navController?.popBackStack()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                TopBar()
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 0.dp
                ) {
                    IconButton(
                        onClick = { navController?.popBackStack() },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = Color(0xFF002366)
                        )
                    }
                    Text(
                        text = "Agregar Evento",
                        color = Color(0xFF002366),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp)
                    )
                }
            }
        },
        bottomBar = {
            AdminBarraNav(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = scaffoldState.snackbarHostState
            ) { data ->
                Snackbar(
                    backgroundColor = Color(0xFF002366),
                    contentColor = Color.White,
                    snackbarData = data
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = fechaText,
                    onValueChange = { fechaText = formatDateInput(it) },
                    label = { Text("Fecha (dd/MM/yyyy)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF002366),
                        unfocusedBorderColor = Color(0xFF002366),
                        cursorColor = Color(0xFF002366),
                        textColor = Color.Black,
                        focusedLabelColor = Color(0xFF002366),
                        unfocusedLabelColor = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = horaText,
                    onValueChange = { horaText = formatTimeInput(it) },
                    label = { Text("Hora (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF002366),
                        unfocusedBorderColor = Color(0xFF002366),
                        cursorColor = Color(0xFF002366),
                        textColor = Color.Black,
                        focusedLabelColor = Color(0xFF002366),
                        unfocusedLabelColor = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF002366),
                        unfocusedBorderColor = Color(0xFF002366),
                        cursorColor = Color(0xFF002366),
                        textColor = Color.Black,
                        focusedLabelColor = Color(0xFF002366),
                        unfocusedLabelColor = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = lugar,
                    onValueChange = { lugar = it },
                    label = { Text("Lugar") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF002366),
                        unfocusedBorderColor = Color(0xFF002366),
                        cursorColor = Color(0xFF002366),
                        textColor = Color.Black,
                        focusedLabelColor = Color(0xFF002366),
                        unfocusedLabelColor = Color(0xFF002366)
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF002366))
                ) {
                    Text(text = "Guardar", color = Color.White)
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = errorMessage!!, color = Color.Red)
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Confirmación") },
                    text = { Text("¿Está seguro de que desea guardar este evento?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                guardarEvento(
                                    fechaText = fechaText,
                                    horaText = horaText,
                                    titulo = titulo,
                                    lugar = lugar,
                                    eventRepository = eventRepository,
                                    onSuccess = {
                                        shouldShowSuccess = true
                                    },
                                    onError = { error ->
                                        errorMessage = error
                                    }
                                )
                            }
                        ) {
                            Text("Sí")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    )
}

fun formatDateInput(input: TextFieldValue): TextFieldValue {
    val text = input.text
    val digits = text.filter { it.isDigit() }

    val formatted = StringBuilder()
    var selectionIndex = input.selection.end

    var i = 0
    var digitIndex = 0

    while (digitIndex < digits.length && i < 10) {
        when (i) {
            2, 5 -> {
                formatted.append('/')
                if (i < selectionIndex) {
                    selectionIndex++
                }
                i++
            }
            else -> {
                formatted.append(digits[digitIndex])
                digitIndex++
                i++
            }
        }
    }

    val newSelection = selectionIndex.coerceAtMost(formatted.length)

    return TextFieldValue(
        text = formatted.toString(),
        selection = TextRange(newSelection)
    )
}

fun formatTimeInput(input: TextFieldValue): TextFieldValue {
    val text = input.text
    val digits = text.filter { it.isDigit() }

    val formatted = StringBuilder()
    var selectionIndex = input.selection.end

    var i = 0
    var digitIndex = 0

    while (digitIndex < digits.length && i < 5) {
        when (i) {
            2 -> {
                formatted.append(':')
                if (i < selectionIndex) {
                    selectionIndex++
                }
                i++
            }
            else -> {
                formatted.append(digits[digitIndex])
                digitIndex++
                i++
            }
        }
    }

    val newSelection = selectionIndex.coerceAtMost(formatted.length)

    return TextFieldValue(
        text = formatted.toString(),
        selection = TextRange(newSelection)
    )
}

fun guardarEvento(
    fechaText: TextFieldValue,
    horaText: TextFieldValue,
    titulo: String,
    lugar: String,
    eventRepository: EventRepository,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "ES"))
    val fechaCompletaText = "${fechaText.text} ${horaText.text}"
    val fecha: Date? = try {
        sdf.parse(fechaCompletaText)
    } catch (e: Exception) {
        null
    }

    if (fecha == null || titulo.isBlank() || lugar.isBlank()) {
        onError("Por favor, ingrese todos los campos correctamente.")
        return
    }

    val evento = Evento(
        fecha = fecha,
        titulo = titulo,
        lugar = lugar
    )

    eventRepository.addEvento(evento, onSuccess = {
        onSuccess()
    }, onError = { error ->
        onError("Error al guardar el evento: $error")
    })
}

@Preview(showBackground = true)
@Composable
fun AgregarEventoPreview() {
    AgregarEvento(navController = null)
}
