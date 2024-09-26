
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
import com.leotesta017.clinicapenal.model.Servicio
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.ModificarInfoTemplate
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun ModificarServiciosInfoAdmin(
    navController: NavController?,
    id: String,
    titulo: String,
    descripcion: String,
    urlimagen: String,
    viewModel: ServicioViewModel = viewModel()
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
                titulo = "Modificar Servicio",
                initialName = nombre,
                initialDescription = descripcion,
                id = id,
                contenido = contenido,
                urlimagen = urlimagen,
                bottomBarContent = {
                    AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
                },
                onSaveClick = {nombre,descripcion,url_imagen,textContent ->
                    CoroutineScope(Dispatchers.IO).launch {
                        var modServicio = Servicio(
                            id = id,
                            titulo = nombre,
                            descripcion = descripcion,
                            contenido = textContent,
                            url_imagen = url_imagen
                        )
                        viewModel.updateServicio(modServicio)
                    }

                },
                onCancelClick = {

                }
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewModificarServiciosAdmin() {
    ModificarServiciosInfoAdmin(
        navController = null,
        id = "123",
        titulo = "Modificar Servicio",
        descripcion = "Descripción del servicio",
        urlimagen = ""
    )
}