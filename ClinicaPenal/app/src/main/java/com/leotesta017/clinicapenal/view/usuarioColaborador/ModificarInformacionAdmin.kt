package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.ModificarInfoTemplate
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ModificarInfoAdmin(
    navController: NavController?,
    id: String,
    titulo: String,
    descripcion: String,
    urlimagen: String,
    viewModel: CategoryViewModel = viewModel()
) {
    val nombre = titulo

    LaunchedEffect(id) {
        viewModel.fetchContenidoById(id)
    }

    val contenido by viewModel.contenido.collectAsState()
    val error by viewModel.error.collectAsState()

    val formattedContent = contenido
        .replace("-", "\n-")
        .replace(Regex("\\*\\*(.*?)\\*\\*"), "\n**$1**\n") // Colocar las negritas en nuevas líneas

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
                contenido = formattedContent, // Pasamos el contenido formateado a la plantilla
                urlimagen = urlimagen,
                bottomBarContent = {
                    AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
                },
                onSaveClick = { nombre, descripcion, url_imagen, textContent ->
                    val contentToSave = textContent
                        .replace("\n-", " -")
                        .replace("\n", " ")
                        .replace(Regex("\\s{2,}"), " ")

                    CoroutineScope(Dispatchers.IO).launch {
                        val modCategoria = Categoria(
                            id = id,
                            titulo = nombre,
                            descripcion = descripcion,
                            contenido = contentToSave,
                            url_imagen = url_imagen
                        )
                        viewModel.updateCategoria(modCategoria)
                    }
                },
                onCancelClick = {}
            )
        }
    }
}