package com.leotesta017.clinicapenal.view.editvideo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Video
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel
import kotlinx.coroutines.launch
import android.util.Log
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@Composable
fun EditVideoScreen(
    navController: NavController,
    videoId: String,
    viewModel: VideoViewModel = viewModel(),
    userModel: UsuarioViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var video by remember { mutableStateOf<Video?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var urlVideo by remember { mutableStateOf(TextFieldValue("")) }
    var tipo by remember { mutableStateOf("General") }

    var expanded by remember { mutableStateOf(false) }
    val tipoOptions = listOf("General", "Estudiante")

    LaunchedEffect(videoId) {
        val fetchedVideo = viewModel.getVideoById(videoId)
        if (fetchedVideo != null) {
            video = fetchedVideo
            titulo = TextFieldValue(fetchedVideo.titulo)
            descripcion = TextFieldValue(fetchedVideo.descripcion)
            urlVideo = TextFieldValue(fetchedVideo.url_video)
            tipo = when (fetchedVideo.tipo.lowercase()) {
                "general" -> "General"
                "estudiante" -> "Estudiante"
                else -> "General"
            }
        } else {
            errorMessage = "Video no encontrado."
        }
        isLoading = false
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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = Color(0xFF002366)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Editar Video",
                        color = Color(0xFF002366),
                        style = MaterialTheme.typography.h6
                    )
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "Error desconocido",
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    video != null -> {
                        EditVideoForm(
                            titulo = titulo,
                            onTituloChange = { titulo = it },
                            descripcion = descripcion,
                            onDescripcionChange = { descripcion = it },
                            urlVideo = urlVideo,
                            onUrlVideoChange = { urlVideo = it },
                            tipo = tipo,
                            onTipoChange = {
                                tipo = it
                                expanded = false
                                Log.d("EditVideoForm", "Selected tipo: $it")
                            },
                            tipoOptions = tipoOptions,
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = it
                                Log.d("EditVideoForm", "Dropdown expanded: $it")
                            },
                            onSave = {
                                if (titulo.text.isBlank() || descripcion.text.isBlank() || urlVideo.text.isBlank()) {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Por favor, completa todos los campos.")
                                    }
                                } else {
                                    coroutineScope.launch {
                                        val updatedVideo = video!!.copy(
                                            titulo = titulo.text,
                                            descripcion = descripcion.text,
                                            url_video = urlVideo.text,
                                            tipo = tipo.lowercase()
                                        )
                                        try {
                                            viewModel.updateVideo(videoId, updatedVideo)
                                            scaffoldState.snackbarHostState.showSnackbar("Video actualizado con éxito.")
                                            navController.popBackStack()
                                        } catch (e: Exception) {
                                            scaffoldState.snackbarHostState.showSnackbar("Error al actualizar el video.")
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EditVideoForm(
    titulo: TextFieldValue,
    onTituloChange: (TextFieldValue) -> Unit,
    descripcion: TextFieldValue,
    onDescripcionChange: (TextFieldValue) -> Unit,
    urlVideo: TextFieldValue,
    onUrlVideoChange: (TextFieldValue) -> Unit,
    tipo: String,
    onTipoChange: (String) -> Unit,
    tipoOptions: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = onTituloChange,
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            maxLines = Int.MAX_VALUE,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF002366),
                unfocusedBorderColor = Color(0xFF002366),
                cursorColor = Color(0xFF002366),
                focusedLabelColor = Color(0xFF002366),
                unfocusedLabelColor = Color(0xFF002366)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = urlVideo,
            onValueChange = onUrlVideoChange,
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

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tipo,
                onValueChange = {},
                label = { Text("Tipo de video") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onExpandedChange(!expanded)
                        Log.d("EditVideoForm", "Dropdown expanded: ${!expanded}")
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF002366),
                    unfocusedBorderColor = Color(0xFF002366),
                    cursorColor = Color(0xFF002366),
                    focusedLabelColor = Color(0xFF002366),
                    unfocusedLabelColor = Color(0xFF002366)
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        onExpandedChange(!expanded)
                        Log.d("EditVideoForm", "Dropdown expanded: ${!expanded}")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Desplegar menú"
                        )
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    onExpandedChange(false)
                    Log.d("EditVideoForm", "Dropdown dismissed")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                tipoOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            onTipoChange(selectionOption)
                            Log.d("EditVideoForm", "Selected tipo: $selectionOption")
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF002366))
        ) {
            Text("Guardar Cambios", color = Color.White)
        }
    }
}
