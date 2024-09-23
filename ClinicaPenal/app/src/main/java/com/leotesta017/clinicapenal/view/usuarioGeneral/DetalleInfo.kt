package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.HeaderSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionContent
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
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

fun preprocesarMarkdown(markdown: String): String {
    return markdown
        .replace("####", "\n####")
        .replace("###", "\n###")
        .replace("- ", "\n• ") // Cambiar guiones por bolitas
        .replace("\n\n", "\n")
}

@Composable
fun CustomMarkdownText(content: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp)) {
        val lines = content.split("\n")
        for (line in lines) {
            when {
                line.startsWith("####") -> {
                    SectionTitle(
                        title = line.removePrefix("####").trim()
                    )
                    Spacer(modifier = Modifier.height(6.dp))  // Reducir el espacio aquí
                }
                line.startsWith("###") -> {
                    Spacer(modifier = Modifier.height(12.dp))  // Reducir el espacio aquí
                    Text(
                        text = line.removePrefix("###").trim(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                            .padding(16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))  // Reducir el espacio aquí
                }
                else -> {
                    Text(
                        text = parseMarkdownToAnnotatedString(line),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))  // Reducir el espacio aquí
                }
            }
        }
    }
}

@Composable
fun parseMarkdownToAnnotatedString(text: String): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val boldRegex = Regex("""\*\*(.*?)\*\*""")
        boldRegex.findAll(text).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last
            append(text.substring(currentIndex, start))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(matchResult.groupValues[1])
            }
            currentIndex = end + 1
        }
        append(text.substring(currentIndex, text.length))
    }
}
