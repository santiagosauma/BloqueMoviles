
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.model.Servicio
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
    // Suponiendo que obtenemos "nombre" basado en el `id`
    val nombre = titulo

    LaunchedEffect(id) {
        viewModel.fetchContenidoById(id)
    }

    // Obtener el contenido del servicio desde el ViewModel
    val contenido by viewModel.contenido.collectAsState()

    // Aquí puedes manejar cualquier error si lo necesitas
    val error by viewModel.error.collectAsState()


    //FUNCIONES PARA EL PROCESAMIENTO MARKDOWN DEL CONTENIDO CON UN GET EN BASE AL ID
    when {
        contenido.isEmpty() && error == null -> {
            Column(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

        }

        error != null -> {
            Text(text = error ?: "Error desconocido", color = Color.Red)
        }

        contenido.isNotEmpty() ->{
            ModificarInfoTemplate(
                navController = navController,
                titulo = "Modificar Categoria",
                initialName = nombre,
                initialDescription = descripcion,
                id = id,
                contenido = contenido,
                urlimagen = urlimagen,

                bottomBarContent = {
                    AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
                },
                onSaveClick = { nombre,descripcion,url_imagen,textContent ->
                    CoroutineScope(Dispatchers.IO).launch {
                        var modCategoria = Categoria(
                            id = id,
                            titulo = nombre,
                            descripcion = descripcion,
                            contenido = textContent,
                            url_imagen = url_imagen
                        )
                        viewModel.updateCategoria(modCategoria)
                    }
                },
                onCancelClick = {
                    // Lógica para cancelar
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewModificarInfoAdmin() {
    ModificarInfoAdmin(
        navController = null, // No se necesita navegación real en la vista previa
        id = "456",
        titulo = "Modificar Información",
        descripcion = "Descripción de la información",
        urlimagen =  ""
    )
}
