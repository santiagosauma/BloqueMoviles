package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// PANTALLA TEMPLATE
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoGenerica

// FUNCIONES GENERALES
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoriaConBoton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBarPantallaInfo
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

// VIEWMODELS
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel

@Composable
fun PantallaInfoAdmin(navController: NavController) {
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
                routeCategoria = "modificar-info",
                routeServicio = "modificar_servicios_info",
                onSearchStarted = onSearchStarted
            )
        },

        noticias = {
            LabelCategoriaConBoton(
                label = "Noticias",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "AccionVideo",
            )
            SpacedItem(spacing = 16) {
                CarruselDeNoticias(
                    navController = navController,
                    viewModel = videoViewModel,
                    userModel = usuarioViewModel
                )
            }
        },
        informacionLegal = { categorias, error ->
            LabelCategoriaConBoton(
                label = "Información Legal",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar-info-admin"
            )

            CategoriesSection(
                navController = navController,
                route = "modificar-info",
                categories = categorias,
                error = error
            )

        },
        servicios = { servicios, error ->
            LabelCategoriaConBoton(
                label = "Servicios",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar_servicios_info-admin"
            )

            ServicesSection(
                navController = navController,
                route = "modificar_servicios_info",
                servicios = servicios,
                error = error
            )

        },
        pantallasExtra = {
            PantallasExtra(
                navController = navController,
                routeJuribot = "JuriBotAdmin",
                routeCrearSolicitud = "generalsolicitudadmin"
            )
        },
        barraNav = {
            Box(modifier = Modifier.fillMaxSize()) {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    )
}
