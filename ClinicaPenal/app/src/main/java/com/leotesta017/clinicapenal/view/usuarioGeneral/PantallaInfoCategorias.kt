
package com.leotesta017.clinicapenal.view.usuarioGeneral



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//FUNCIONES GENERALES
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CarruselDeNoticias
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CategoriesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.LabelCategoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ServicesSection
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.PantallasExtra
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem

//FUNCIONES VIEW MODEL
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun PantallaInfoCategorias(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                SpacedItem {
                    TopBar()
                }

                SpacedItem {
                    SearchBar(searchText = "")
                }

                SpacedItem {
                    CarruselDeNoticias()
                }


                LabelCategoria(
                    label = "Información Legal",
                    modifier = Modifier.padding(start = 36.dp)
                )

                SpacedItem {
                    CategoriesSection(
                        navController = navController,
                        viewModel =  CategoryViewModel(),
                        route = "detalle_info"
                    )
                }

                LabelCategoria(
                    label = "Servicios",
                    modifier = Modifier.padding(start = 36.dp)
                )

                SpacedItem {
                    ServicesSection(
                        navController = navController,
                        viewModel = ServicioViewModel(),
                        route = "servicios_info"
                    )
                }

            }
        }

        PantallasExtra(
            navController = navController,
            routeJuribot = "Juribot",
            routeCrearSolicitud = "crearsolicitud"
        )

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaInfoCategoriasPreview() {
    PantallaInfoCategorias(navController = null)
}
