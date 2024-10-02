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

    // Formateamos el contenido de forma más controlada para no romper los pares de negritas
    val formattedContent = contenido
        .replace("-", "\n-") // Aseguramos que las listas comiencen en una nueva línea
        .replace(Regex("\\*\\*(.*?)\\*\\*"), "\n**$1**\n") // Colocamos las negritas completas en líneas separadas
        .replace(Regex("(?<=\\*)\\*\\*(?!\\*)"), "") // Aseguramos que no haya salto entre pares de asteriscos
        .replace(Regex("(?<!\\*)\\*\\*(?=\\w)"), "\n**") // Aseguramos que una nueva sección de negritas comience en una nueva línea
        .trim() // Quitamos cualquier espacio en blanco innecesario al final o al inicio

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
                bottomBarContent = {
                    AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
                },
                onSaveClick = { nombre, descripcion, url_imagen, textContent ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val modCategoria = Categoria(
                            id = id,
                            titulo = nombre,
                            descripcion = descripcion,
                            contenido = textContent,
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
