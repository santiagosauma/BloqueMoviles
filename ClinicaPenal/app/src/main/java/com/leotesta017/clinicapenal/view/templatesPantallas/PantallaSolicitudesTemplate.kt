package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
@Composable
fun GenerarSolicitudPantallaTemplatenavController(
    navController: NavController?,
    titulo1: String,
    items1: List<Any>,
    itemComposable1: @Composable (Any) -> Unit,
    titulo2: String,
    items2: List<Any>,
    itemComposable2: @Composable (Any) -> Unit,
    barraNavComposable: @Composable () -> Unit
)
{
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar(searchText = "")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = titulo1,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(items1) { item ->
                itemComposable1(item)
                Spacer(modifier = Modifier.height(4.dp))
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = titulo2,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(items2) { item ->
                itemComposable2(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        barraNavComposable()
    }
}