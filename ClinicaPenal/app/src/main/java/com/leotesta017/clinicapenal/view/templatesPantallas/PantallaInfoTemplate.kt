package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SpacedItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@Composable
fun PantallaInfoGenerica(
    navController: NavController?,
    topBar: @Composable () -> Unit = { TopBar() },
    searchBar: @Composable () -> Unit = { SearchBar(searchText = "") },
    noticias: @Composable () -> Unit,
    informacionLegal: @Composable () -> Unit,
    servicios: @Composable () -> Unit,
    pantallasExtra: @Composable () -> Unit,
    barraNav: @Composable () -> Unit
)
{
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
                SpacedItem { topBar() }
                SpacedItem { searchBar() }
                noticias()
                informacionLegal()
                servicios()
            }
        }

        pantallasExtra()

        barraNav()
    }
}
