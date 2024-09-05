package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.usuarioGeneral.CategoriesSection
import com.leotesta017.clinicapenal.usuarioGeneral.ServicesSection

@Composable
fun PantallaInfoAdmin(navController: NavController?) {
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
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar("")
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoriaConBoton(
                    label = "Noticias",
                    navController = navController,
                    modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CarruselDeNoticias(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoriaConBoton(
                    label = "Información Legal",
                    navController = navController,
                    modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                )
                CategoriesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoriaConBoton(
                    label = "Servicios",
                    navController = navController,
                    modifier = Modifier.padding(start = 36.dp)
                )
                ServicesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 95.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(
                icon = Icons.AutoMirrored.Filled.Chat,
                label = "JuriBot",
                onClick = { navController?.navigate("Juribot") }
            )
            RoundedButton(
                icon = Icons.Default.CalendarToday,
                label = "Solicitud de Cita",
                onClick = { navController?.navigate("crearsolicitud") }
            )
        }

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselDeNoticias(navController: NavController?) {
    val totalPages = 5
    val pagerState = rememberPagerState { totalPages }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 8.dp)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.90f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Imagen ${page + 1}", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun LabelCategoriaConBoton(label: String, navController: NavController?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // Alinea los elementos verticalmente al centro
        horizontalArrangement = Arrangement.SpaceBetween, // Distribuye el texto y el botón
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp) // Ajusta el padding horizontal
    ) {
        // El label se ajustará con el peso 1 para ocupar el espacio restante
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f) // Deja que el texto ocupe el espacio restante
        )

        // Botón de agregar
        Button(
            onClick = { navController?.navigate("modificar-info") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B1F8C), // Color azul del botón
                contentColor = Color.White // Texto e icono en blanco
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .height(40.dp) // Ajustar la altura del botón
                .padding(end = 10.dp, start = 10.dp) // Asegura que haya espacio entre el texto y el botón
        ) {
            Icon(
                imageVector = Icons.Filled.Add, // Icono de agregar
                contentDescription = "Agregar"
            )
            Spacer(modifier = Modifier.width(4.dp)) // Espacio entre el icono y el texto
            Text(text = "Agregar")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PantallaInfoCategoriasPreview() {
    PantallaInfoAdmin(navController = null)
}
