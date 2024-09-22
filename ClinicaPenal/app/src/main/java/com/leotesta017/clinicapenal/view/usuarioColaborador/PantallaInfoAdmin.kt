package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//PANTALLA TEMPLATE
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaInfoGenerica

//FUNCIONES GENERALES
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

//VIEWMODEL
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

@Composable
fun PantallaInfoAdmin(navController: NavController?) {
    PantallaInfoGenerica(
        navController = navController,
        noticias = {
            com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoriaConBoton(
                label = "Noticias",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "modificar-info"
            )
            SpacedItem {
                com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias(viewModel = VideoViewModel())
            }
        },
        informacionLegal = {
            com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoriaConBoton(
                label = "Información Legal",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar-info-admin"
            )

            SpacedItem {
                CategoriesSection(
                    navController = navController,
                    viewModel = CategoryViewModel(),
                    route = "modificar-info"
                )
            }
        },
        servicios = {
            com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoriaConBoton(
                label = "Servicios",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar_servicios_info-admin"
            )

            SpacedItem {
                ServicesSection(
                    navController = navController,
                    viewModel = ServicioViewModel(),
                    route = "modificar_servicios_info"
                )
            }
        },
        pantallasExtra = {
            PantallasExtra(
                navController = navController,
                routeJuribot = "JuriBotAdmin",
                routeCrearSolicitud = "generalsolicitudadmin"
            )
        },
        barraNav = {
            Box(modifier = Modifier.fillMaxSize())
            {
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

@Preview(showBackground = true)
@Composable
fun PantallaInfoCategoriasPreview() {
    PantallaInfoAdmin(navController = null)
}
