package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CustomMarkdownText
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.HeaderSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionContent
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.preprocesarMarkdown
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaDetalleItemTemplate
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel


@Composable
fun ServiciosInfo(
    navController: NavController?,
    titulo: String,
    descripcion: String,
    servicioId: String,
    contenidoParam: String? = null,
    viewModel: ServicioViewModel = viewModel()

) {

    // Llamar a fetchContenidoById para cargar el contenido del servicio
    LaunchedEffect(servicioId) {
        if (contenidoParam == null) {
            viewModel.fetchContenidoById(servicioId)
        }

    }

    // Obtener el contenido del servicio desde el ViewModel
    val contenido by viewModel.contenido.collectAsState(initial = contenidoParam ?: "")

    // Aquí puedes manejar cualquier error si lo necesitas
    val error by viewModel.error.collectAsState()


    PantallaDetalleItemTemplate(
        navController = navController,
        bottomBar = {
            BarraNav(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E))
            )
        },
        content = {
            HeaderSection(titulo, navController)
            Spacer(modifier = Modifier.height(8.dp))  // Reducir el espacio aquí
            SectionTitle("Descripción")
            Spacer(modifier = Modifier.height(4.dp).padding(end = 0.dp))  // Reducir el espacio aquí
            SectionContent(descripcion)

            when {
                contenido.isEmpty() && error == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                error != null -> {
                    Text(text = error ?: "Error desconocido", color = Color.Red)
                }
                contenido.isNotEmpty() -> {
                    val processedContent = preprocesarMarkdown(contenido)
                    CustomMarkdownText(content = processedContent)
                }
                else -> {
                    Text(text = "No hay contenido disponible", color = Color.Gray)
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ServiciosInfoPreview() {
    val titulo = "Servicio X"
    val descripcion = "detalle de descripcion"
    val id = "1"
    ServiciosInfo(
        navController = null,
        titulo = titulo,
        descripcion =  descripcion,
        servicioId = id
    )
}
