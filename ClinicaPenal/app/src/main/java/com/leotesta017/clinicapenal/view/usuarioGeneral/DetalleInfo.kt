package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CustomMarkdownText
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.HeaderSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionContent
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.preprocesarMarkdown
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaDetalleItemTemplate
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel

@Composable
fun DetalleInfo(
    navController: NavController?,
    titulo: String,
    descripcion: String,
    categoriaId: String,
    contenidoParam: String? = null,
    viewModel: CategoryViewModel = viewModel()
) {
    LaunchedEffect(categoriaId) {
        if (contenidoParam == null) {
            viewModel.fetchContenidoById(categoriaId)
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


