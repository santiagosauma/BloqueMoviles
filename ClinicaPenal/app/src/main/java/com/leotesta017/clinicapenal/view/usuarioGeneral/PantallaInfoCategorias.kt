
package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//TEMPLATE DE LA VISTA
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoGenerica

//FUNCIONES GENERALES
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.MyTextNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBarPantallaInfo
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

//FUNCIONES VIEW MODEL
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

@Composable
fun PantallaInfoCategorias(navController: NavController?) {
    PantallaInfoGenerica(
        navController = navController,

        searchBar = { categorias, servicios, errorCategoria, errorServicio, onSearchStarted  ->
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

        noticias = {
            SpacedItem(spacing = 16) {
                CarruselDeNoticias(
                    viewModel = VideoViewModel(),
                    contentText = {
                        MyTextNoticias(text = "Noticias")
                    }
                )
            }
        },

        informacionLegal = { categorias, error ->
            LabelCategoria(
                label = "Información Legal",
                modifier = Modifier.padding(start = 16.dp)
            )

            CategoriesSection(
                navController = navController,
                route = "detalle_info",
                categories = categorias,
                error = error
            )

        },

        servicios = { servicios, error ->
            LabelCategoria(
                label = "Servicios",
                modifier = Modifier.padding(start = 16.dp)
            )

            ServicesSection(
                navController = navController,
                route = "servicios_info",
                servicios = servicios,
                error = error
            )

        },

        pantallasExtra = {

            PantallasExtra(
                navController = navController,
                routeJuribot = "Juribot",
                routeCrearSolicitud = "crearsolicitud"
            )
        },

        barraNav = {
            Box(modifier = Modifier.fillMaxSize())
            {
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

@Preview(showBackground = true)
@Composable
fun PantallaInfoCategoriasPreview() {
    PantallaInfoCategorias(navController = null)
}