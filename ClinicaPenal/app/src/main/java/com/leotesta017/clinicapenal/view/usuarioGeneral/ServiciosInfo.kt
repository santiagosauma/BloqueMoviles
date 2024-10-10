package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.*
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaDetalleItemTemplate
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun ServiciosInfo(
    navController: NavController?,
    titulo: String,
    descripcion: String,
    servicioId: String,
    url_imagen: String,
    contenidoParam: String? = null,
    viewModel: ServicioViewModel = viewModel()
) {
    LaunchedEffect(servicioId) {
        if (contenidoParam == null) {
            viewModel.fetchContenidoById(servicioId)
        }
    }

    val contenido by viewModel.contenido.collectAsState(initial = contenidoParam ?: "")
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
            Column(modifier = Modifier.padding(horizontal = 16.dp)) { // Agregar padding horizontal
                HeaderSection(titulo, navController)
                Spacer(modifier = Modifier.height(8.dp))

                Image(
                    painter = rememberAsyncImagePainter(url_imagen),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle("Descripción")
                Spacer(modifier = Modifier.height(4.dp))
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ServiciosInfoPreview() {
    val titulo = "Servicio X"
    val descripcion = "detalle de descripcion"
    val id = "1"
    val url_imagen = "https://example.com/image.jpg"
    ServiciosInfo(
        navController = null,
        titulo = titulo,
        descripcion = descripcion,
        servicioId = id,
        url_imagen = url_imagen
    )
}
