package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton2
import kotlinx.coroutines.launch

@Composable
fun PantallaModificarInformacionTemplate(
    navController: NavController?,
    titulo: String,
    textContenido: String,
    textName: String,
    textDescripcion: String,
    textURL: String,
    route: String,
    bottomBar: @Composable () -> Unit,
    contentText: @Composable (String, (String) -> Unit) -> Unit,
    contentName: @Composable (String, (String) -> Unit) -> Unit,
    contentDescripcion: @Composable (String, (String) -> Unit) -> Unit,
    contentImage: @Composable (String, (String) -> Unit) -> Unit
) {
    var textURLContent by remember { mutableStateOf(textURL) }
    var textDescripcionContent by remember { mutableStateOf(textDescripcion) }
    var textNameContent by remember { mutableStateOf(textName) }
    var textContent by remember { mutableStateOf(textContenido) }
    var showDiscardDialog by remember { mutableStateOf(false) }
    var isModified by remember { mutableStateOf(false) }
    BackHandler(enabled = isModified) {
        showDiscardDialog = true
    }

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
                    IconButton(onClick = {
                        if (isModified) {
                            showDiscardDialog = true
                        } else {
                            navController?.navigate(route = route)
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        },
        bottomBar = {
            Column {
                bottomBar()
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    contentName(textNameContent){ newName ->
                        textNameContent = newName
                        isModified = true
                    }
                    contentDescripcion(textDescripcionContent){newDescription ->
                        textDescripcionContent = newDescription
                        isModified = true
                    }
                    contentImage(textURLContent){newURL ->
                        textURLContent = newURL
                        isModified = true
                    }
                    contentText(textContent) { newText ->
                        textContent = newText
                        isModified = true
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = { Text("Descartar cambios") },
            text = { Text("¿Estás seguro de que deseas descartar los cambios?") },
            confirmButton = {
                Button(onClick = {
                    showDiscardDialog = false
                    navController?.popBackStack()
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(onClick = { showDiscardDialog = false }) {
                    Text("No")
                }
            }
        )
    }
}

@Composable
fun ModificarInfoTemplate(
    navController: NavController?,
    titulo: String,
    initialName: String,
    initialDescription: String,
    id: String,
    contenido: String,
    urlimagen: String,
    route: String,
    bottomBarContent: @Composable () -> Unit,
    onSaveClick: (String, String, String, String) -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var nombre by remember { mutableStateOf(initialName) }
    var descripcion by remember { mutableStateOf(initialDescription) }
    var url_imagen by remember { mutableStateOf(urlimagen) }
    var textContent by remember { mutableStateOf(contenido) }
    var isModified by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    var isEliminar by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    Box(modifier = Modifier.fillMaxSize()) {
        PantallaModificarInformacionTemplate(
            navController = navController,
            route = route,
            titulo = titulo,
            textDescripcion = textContent,
            textContenido =  descripcion,
            textName = nombre,
            textURL = url_imagen,
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    RoundedButton(
                        icon = Icons.Default.Save,
                        label = "Guardar",
                        onClick = {
                            onSaveClick(nombre, descripcion, url_imagen, textContent)
                            isModified = false
                        }
                    )
                    RoundedButton(
                        icon = Icons.Default.Cancel  ,
                        label = "Cancelar",
                        onClick = {
                            if (isModified) {
                                onCancelClick()
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("No hay cambios sin guardar")
                                }
                            }
                        }
                    )
                    RoundedButton2(
                        icon = Icons.Default.Delete,
                        label = "",
                        onClick = {
                            onDeleteClick()
                        }
                    )
                }
                bottomBarContent()
            },
            contentName = {textName, onNameChange,->
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        isModified = true },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))

            },
            contentDescripcion = {textContenido,onTextContenidoChange,->
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = {
                        descripcion = it
                        isModified = true
                    },
                    label = { Text("descripcipcion") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
            },
            contentImage = {textURL,onURLChange->
                OutlinedTextField(
                    value = url_imagen,
                    onValueChange = {
                        url_imagen = it
                        isModified = true
                    },
                    label = { Text("URL de la imagen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
            },
            contentText = { textDescripcion, onTextChange, ->
                OutlinedTextField(
                    value = textContent,
                    onValueChange = {
                        textContent = it
                        isModified = true
                    },
                    label = { Text("Contenido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    minLines = 6,
                    maxLines = Int.MAX_VALUE
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        )
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
