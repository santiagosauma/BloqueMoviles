package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.ModificarInfoTemplate
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button

@Composable
fun ModificarInfoCategoriaAdmin(
    navController: NavController?,
    id: String,
    titulo: String,
    descripcion: String,
    urlimagen: String,
    viewModel: CategoryViewModel = viewModel()
) {
    val nombre = titulo
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf("") }
    var showDiscardDialog by remember { mutableStateOf(false) } // Estado para controlar el diálogo

    LaunchedEffect(id) {
        viewModel.fetchContenidoById(id)
    }

    val contenido by viewModel.contenido.collectAsState()
    val error by viewModel.error.collectAsState()

    val formattedContent = contenido
        .replace("-", "\n-")
        .replace(Regex("\\*\\*(.*?)\\*\\*"), "\n**$1**\n")

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            contenido.isEmpty() && error == null -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            error != null -> {
                Text(text = error ?: "Error desconocido", color = Color.Red)
            }
            contenido.isNotEmpty() -> {
                ModificarInfoTemplate(
                    navController = navController,
                    titulo = "Modificar Categoria",
                    initialName = nombre,
                    initialDescription = descripcion,
                    id = id,
                    contenido = formattedContent,
                    urlimagen = urlimagen,
                    route = "pantallainfoadmin",
                    bottomBarContent = {
                        AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
                    },
                    onSaveClick = { nombre, descripcion, url_imagen, textContent ->
                        val contentToSave = textContent
                            .replace("\n-", " -")
                            .replace("\n", " ")
                            .replace(Regex("\\s{2,}"), " ")

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val modCategoria = Categoria(
                                    id = id,
                                    titulo = nombre,
                                    descripcion = descripcion,
                                    contenido = contentToSave,
                                    url_imagen = url_imagen
                                )
                                viewModel.updateCategoria(modCategoria)
                                snackbarMessage = "Contenido actualizado con éxito"
                                scope.launch { snackbarHostState.showSnackbar(snackbarMessage) }
                            } catch (e: Exception) {
                                snackbarMessage = "Error al actualizar el contenido"
                                scope.launch { snackbarHostState.showSnackbar(snackbarMessage) }
                            }
                        }
                    },
                    onCancelClick = {
                        showDiscardDialog = true
                    }
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Snackbar(
                snackbarData = it,
                containerColor = Color(0xFF303665),
                contentColor = Color.White
            )
        }

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
}