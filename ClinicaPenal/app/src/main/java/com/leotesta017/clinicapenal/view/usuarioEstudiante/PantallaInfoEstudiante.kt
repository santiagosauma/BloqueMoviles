package com.leotesta017.clinicapenal.view.usuarioEstudiante

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
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoriaConBoton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

//VIEW MODEL
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

@Composable
fun PantallaInfoEstudiante(navController: NavController?) {
    PantallaInfoGenerica(
        navController = navController,

        //SECCION DE NOTICIAS
        noticias = {
            LabelCategoriaConBoton(
                label = "Noticias",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "modificar-info"
            )
            SpacedItem(spacing = 16) {
                CarruselDeNoticias(viewModel = VideoViewModel())
            }
        },

        //SECCION DE INFORMACION LEGAL
        informacionLegal = {
            LabelCategoriaConBoton(
                label = "Información Legal",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar-info-estudiante"
            )

            CategoriesSection(
                    navController = navController,
                    viewModel = CategoryViewModel(),
                    route = "modificar-info"
            )

        },

        //SECCION DE SERVICIOS
        servicios = {
            LabelCategoriaConBoton(
                label = "Servicios",
                navController = navController,
                modifier = Modifier.padding(0.dp),
                navigateroute = "agregar-servicio-estudiante"
            )

            ServicesSection(
                    navController = navController,
                    viewModel = ServicioViewModel(),
                    route = "modificar_servicios_info"
            )

        },

        //SECCION DE JURIBOT Y GENERARSOLICITUD
        pantallasExtra = {
            PantallasExtra(
                navController = navController,
                routeJuribot = "JuriBotAdmin",
                routeCrearSolicitud = "generasolicitudestudiante"
            )
        },

        //SECCION INFERIOR DE LA BARRA DE NAVEGACION
        barraNav = {
            Box(modifier =  Modifier.fillMaxSize())
            {
                EstudiantesBarraNav(
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
fun PreviewPantallaInfoEstudiante() {
    PantallaInfoEstudiante(navController = null)
}
