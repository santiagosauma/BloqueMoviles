package com.leotesta017.clinicapenal.view.editvideo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Video
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVideoScreen(
    navController: NavController,
    videoId: String,
    viewModel: VideoViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var video by remember { mutableStateOf<Video?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Form state
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var urlVideo by remember { mutableStateOf(TextFieldValue("")) }
    var tipo by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(videoId) {
        val fetchedVideo = viewModel.getVideoById(videoId)
        if (fetchedVideo != null) {
            video = fetchedVideo
            titulo = TextFieldValue(fetchedVideo.titulo)
            descripcion = TextFieldValue(fetchedVideo.descripcion)
            urlVideo = TextFieldValue(fetchedVideo.url_video)
            tipo = TextFieldValue(fetchedVideo.tipo)
        } else {
            errorMessage = "Video no encontrado."
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Video") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
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
                        color = MaterialTheme.colorScheme.error,
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
                        onTipoChange = { tipo = it },
                        onSave = {
                            val updatedVideo = video!!.copy(
                                titulo = titulo.text,
                                descripcion = descripcion.text,
                                url_video = urlVideo.text,
                                tipo = tipo.text
                            )
                            viewModel.updateVideo(videoId, updatedVideo) { success ->
                                if (success) {
                                    navController.popBackStack()
                                } else {
                                    errorMessage = "Error al actualizar el video."
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EditVideoForm(
    titulo: TextFieldValue,
    onTituloChange: (TextFieldValue) -> Unit,
    descripcion: TextFieldValue,
    onDescripcionChange: (TextFieldValue) -> Unit,
    urlVideo: TextFieldValue,
    onUrlVideoChange: (TextFieldValue) -> Unit,
    tipo: TextFieldValue,
    onTipoChange: (TextFieldValue) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        OutlinedTextField(
            value = titulo,
            onValueChange = onTituloChange,
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = descripcion,
            onValueChange = onDescripcionChange,
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = urlVideo,
            onValueChange = onUrlVideoChange,
            label = { Text("URL del Video") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = tipo,
            onValueChange = onTipoChange,
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }
    }
}
