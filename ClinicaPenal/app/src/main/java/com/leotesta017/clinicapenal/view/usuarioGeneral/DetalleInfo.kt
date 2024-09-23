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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.HeaderSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionContent
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaDetalleItemTemplate
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun DetalleInfo(
    navController: NavController?,
    titulo: String,
    descripcion: String,
    categoriaId: String,
    viewModel: CategoryViewModel = viewModel()

) {
    // Llamar a fetchContenidoById para cargar el contenido del servicio
    LaunchedEffect(categoriaId) {
        viewModel.fetchContenidoById(categoriaId)
    }

    // Obtener el contenido del servicio desde el ViewModel
    val contenido by viewModel.contenido.collectAsState()

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
            SpacedItem(spacing = 16) {
                SectionTitle("Descripción")
            }
            SpacedItem(spacing = 10 ){
                SectionContent(descripcion)
            }

            //FUNCIONES PARA EL PROCESAMIENTO MARKDOWN DEL CONTENIDO CON UN GET EN BASE AL ID
            when {
                contenido.isEmpty() && error == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                error != null -> {
                    Text(text = error ?: "Error desconocido", color = Color.Red)
                }
                contenido.isNotEmpty() ->{


                    //EDITAR ESTA PARTE PARA EL MARKDOWN DEL CONTENIDO
                    SpacedItem(spacing = 16) {
                        SectionContent(content = contenido)
                    }

                }
                else -> {
                    Text(text = "No hay categorías disponibles", color = Color.Gray)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetalleInfoPreview() {
    val titulo = "Robo"
    val descripcion = "detalle de descripcion"
    val id = "1"
    DetalleInfo(
        navController = null,
        titulo = titulo,
        descripcion =  descripcion,
        categoriaId = id)
}
