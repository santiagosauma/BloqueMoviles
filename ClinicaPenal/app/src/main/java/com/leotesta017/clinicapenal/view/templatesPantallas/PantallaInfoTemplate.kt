@file:Suppress("NAMED_ARGUMENTS_NOT_ALLOWED")

package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.model.Servicio
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun PantallaInfoGenerica(
    navController: NavController?,
    topBar: @Composable () -> Unit = { TopBar() },
    searchBar: @Composable (categorias: List<Categoria>,
                            servicios: List<Servicio>,
                            errorCategoria: String?,
                            errorServicio: String?,
                            onSearchStarted: (Boolean) -> Unit) -> Unit,

    noticias: @Composable () -> Unit,
    informacionLegal: @Composable (categorias: List<Categoria>,
                                   error: String?) -> Unit,
    servicios: @Composable (servicios: List<Servicio>,
                            error: String?) -> Unit,
    pantallasExtra: @Composable () -> Unit,
    barraNav: @Composable () -> Unit,
    categoryViewModel: CategoryViewModel = viewModel(),
    servicioViewModel: ServicioViewModel = viewModel()
) {
    val categoriesLista by categoryViewModel.categorias.collectAsState()
    val categoryError by categoryViewModel.error.collectAsState()

    val serviciosLista by servicioViewModel.servicios.collectAsState()
    val servicioError by servicioViewModel.error.collectAsState()

    // Aquí creamos el estado isBusqueda para controlar si se está buscando
    var isBusqueda by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 160.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                SpacedItem(spacing = 16) {
                    topBar()
                }
                SpacedItem(spacing = 16) {
                    searchBar(
                        categoriesLista,
                        serviciosLista,
                        categoryError,
                        servicioError,
                        onSearchStarted = { searchActive ->
                            isBusqueda = searchActive
                        }
                    )
                }

                if (!isBusqueda) {
                    noticias()
                    informacionLegal(categoriesLista, categoryError)
                    servicios(serviciosLista, servicioError)
                }
            }
        }
        pantallasExtra()
        barraNav()
    }
}

