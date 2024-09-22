package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Ejemplo
import com.leotesta017.clinicapenal.model.Recursos
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun ServiciosInfo(navController: NavController?) {
    // Obtén el servicioId desde la navegación
    val servicioId = navController?.currentBackStackEntry?.arguments?.getString("servicioId") ?: ""

    // Llama a la función interna que maneja la lógica y la UI
    ServicioInfoContent(navController, servicioId)
}

@Composable
fun ServicioInfoContent(
    navController: NavController?,
    servicioId: String
) {
    val viewModel: ServicioViewModel = viewModel()
    val servicioBasico by viewModel.serviciosBasicos.collectAsState()
    var recursos by remember { mutableStateOf<List<Recursos>>(emptyList()) }
    var ejemplos by remember { mutableStateOf<List<Ejemplo>>(emptyList()) }

    // Cargar datos cuando la vista se monta
    LaunchedEffect(servicioId) {
        val result = viewModel.fetchRecursosYejemplos(servicioId)
        recursos = result.first
        ejemplos = result.second
    }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BarraNav(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E))
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Encuentra el servicio seleccionado por ID
            val servicio = servicioBasico.find { it.id == servicioId }
            servicio?.let {
                ServiciosHeaderSection(it.titulo, navController)
                Spacer(modifier = Modifier.height(16.dp))

                ServiciosSectionTitle("Descripción")
                Spacer(modifier = Modifier.height(8.dp))
                ServiciosSectionContent(it.descripcion)
                Spacer(modifier = Modifier.height(16.dp))

                ServiciosSectionTitle("Ejemplos Comunes")
                Spacer(modifier = Modifier.height(8.dp))
                ServiciosSectionContent(ejemplos.joinToString("\n") { ejemplo -> ejemplo.descripcion })
                Spacer(modifier = Modifier.height(16.dp))

                ServiciosSectionTitle("Recursos")
                Spacer(modifier = Modifier.height(8.dp))
                ServiciosSectionContent(recursos.joinToString("\n") { recurso -> recurso.url_linkrecurso })
            } ?: run {
                // Mostrar un mensaje si no se encuentra el servicio
                ServiciosSectionContent("Servicio no encontrado")
            }
        }
    }
}



@Composable
fun ServiciosHeaderSection(title: String, navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ServiciosSectionTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ServiciosSectionContent(content: String) {
    Text(
        text = content,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ServiciosInfoPreview() {
    ServiciosInfo(navController = null)
}
