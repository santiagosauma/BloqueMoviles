package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// TEMPLATE DE LA VISTA
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoGenerica

// FUNCIONES GENERALES
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.MyTextNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBarPantallaInfo
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

// VIEWMODELS
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel

@Composable
fun PantallaInfoCategorias(navController: NavController) {
    val videoViewModel: VideoViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    PantallaInfoGenerica(
        navController = navController,

        searchBar = { categorias, servicios, errorCategoria, errorServicio, onSearchStarted ->
            SearchBarPantallaInfo(
                searchText = "",
                onSearchTextChange = {},
                categorias = categorias,
                servicios = servicios,
                errorCategoria = errorCategoria,
                errorServicio = errorServicio,
                navController = navController,
                routeCategoria = "detalle_info",
                routeServicio = "servicios_info",
                onSearchStarted = onSearchStarted
            )
        },

        // SECCION DE NOTICIAS
        noticias = {
            SpacedItem(spacing = 16) {
                CarruselDeNoticias(
                    navController = navController,
                    viewModel = videoViewModel,
                    userModel = usuarioViewModel,
                    contentText = {
                        MyTextNoticias(text = "Noticias")
                    }
                )
            }
        },

        // SECCION DE INFORMACION LEGAL
        informacionLegal = { categorias, error ->
            LabelCategoria(
                label = "Información Legal"
            )

            CategoriesSection(
                navController = navController,
                route = "detalle_info",
                categories = categorias,
                error = error
            )
        },

        // SECCION DE SERVICIOS
        servicios = { servicios, error ->
            LabelCategoria(
                label = "Servicios"
            )

            ServicesSection(
                navController = navController,
                route = "servicios_info",
                servicios = servicios,
                error = error
            )
        },

        // SECCION DE JURIBOT Y GENERARSOLICITUD
        pantallasExtra = {
            PantallasExtra(
                navController = navController,
                routeJuribot = "Juribot",
                routeCrearSolicitud = "crearsolicitud"
            )
        },

        // SECCION INFERIOR DE LA BARRA DE NAVEGACION
        barraNav = {
            Box(modifier = Modifier.fillMaxSize()) {
                BarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    )
}
