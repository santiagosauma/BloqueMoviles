package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.repository.VideoRepository
import kotlinx.coroutines.launch

@Composable
fun AccionVideo(navController: NavController?) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("general") }
    var titulo by remember { mutableStateOf("") }
    var urlVideo by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                TopBar()  // La TopBar que usas en toda la app
                TopAppBar(
                    backgroundColor = Color.White,
                    elevation = 0.dp
                ) {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver atrás", tint = Color(0xFF002366))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Agregar Video", color = Color(0xFF002366), style = MaterialTheme.typography.h6)
                }
            }
        },
        bottomBar = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
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
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF002366),
                        unfocusedBorderColor = Color(0xFF002366),
                        cursorColor = Color(0xFF002366),
                        focusedLabelColor = Color(0xFF002366),
                        unfocusedLabelColor = Color(0xFF002366)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                var expanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedTextField(
                        value = if (tipo == "general") "General" else "Estudiante",
                        onValueChange = {},
                        label = { Text("Tipo de video") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF002366),
                            unfocusedBorderColor = Color(0xFF002366),
                            cursorColor = Color(0xFF002366),
                            focusedLabelColor = Color(0xFF002366),
                            unfocusedLabelColor = Color(0xFF002366)
                        ),
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Desplegar menú")
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            tipo = "general"
                            expanded = false
                        }) {
                            Text("General")
                        }
                        DropdownMenuItem(onClick = {
                            tipo = "estudiante"
                            expanded = false
                        }) {
                            Text("Estudiante")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
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
                    value = urlVideo,
                    onValueChange = { urlVideo = it },
                    label = { Text("URL del Video") },
                    modifier = Modifier.fillMaxWidth(),
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
                    onClick = {
                        coroutineScope.launch {
                            val isSuccess = VideoRepository().addVideo(
                                descripcion = descripcion,
                                tipo = tipo,
                                titulo = titulo,
                                urlVideo = urlVideo
                            )
                            if (isSuccess) {
                                successMessage = "Video guardado con éxito"
                                navController?.popBackStack()
                            } else {
                                scaffoldState.snackbarHostState.showSnackbar("Error al guardar el video")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF002366))
                ) {
                    Text("Guardar", color = Color.White)
                }

                successMessage?.let {
                    Text(text = it, color = Color(0xFF002366), modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
    )
}
