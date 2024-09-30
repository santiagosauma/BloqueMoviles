
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
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

//FUNCIONES VIEW MODEL
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

@Composable
fun PantallaInfoCategorias(navController: NavController?) {
    PantallaInfoGenerica(
        navController = navController,

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

        informacionLegal = {
            LabelCategoria(
                label = "Información Legal",
                modifier = Modifier.padding(start = 16.dp)
            )

            CategoriesSection(
                    navController = navController,
                    viewModel = CategoryViewModel(),
                    route = "detalle_info"
            )

        },

        servicios = {
            LabelCategoria(
                label = "Servicios",
                modifier = Modifier.padding(start = 16.dp)
            )

            ServicesSection(
                    navController = navController,
                    viewModel = ServicioViewModel(),
                    route = "servicios_info"
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
