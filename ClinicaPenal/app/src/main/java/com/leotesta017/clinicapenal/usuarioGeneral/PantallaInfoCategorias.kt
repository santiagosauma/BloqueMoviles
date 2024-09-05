package com.leotesta017.clinicapenal.usuarioGeneral

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar

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
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar("")
                Spacer(modifier = Modifier.height(16.dp))
                CarruselDeNoticias()
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoria(
                    label = "Información Legal",
                    modifier = Modifier.padding(start = 36.dp)
                )
                CategoriesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoria(
                    label = "Servicios",
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
                onClick = { navController?.navigate("ReviewComentarios") }
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
fun CarruselDeNoticias() {
    val totalPages = 5
    val pagerState = rememberPagerState { totalPages }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 8.dp) // Padding a 16.dp a la izquierda
    ) {
        Text(
            text = "Noticias",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 8.dp)

        )

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
                        .graphicsLayer {
                            translationX = 50f
                        }
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
fun LabelCategoria(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
fun CategoriesSection(navController: NavController?) {
    Column(modifier = Modifier.padding(16.dp)) {
        CategoryItem(
            title = "Violencia Doméstica",
            description = "Maltrato físico o emocional dentro del ámbito familiar",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp)) // Más espacio entre los ítems
        CategoryItem(
            title = "Adeudo",
            description = "Falta de pago de una deuda o compromiso financiero.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp)) // Más espacio entre los ítems
        CategoryItem(
            title = "Vehicular",
            description = "Infracción de delito relacionado con el uso indebido de vehículos.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItem(
            title = "Robo y hurto",
            description = "Tomar algo ajeno sin permiso, con intención de no devolverlo.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItem(
            title = "Extorsión y Amenaza",
            description = "Uso de amenazas o coerción para obtener dinero o bienes mediante intimidación.",
            navController = navController
        )
    }
}

@Composable
fun CategoryItem(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp) // Padding horizontal y vertical
            .clickable {
                navController?.navigate("detalle_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Cuadro más pequeño
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Img", color = Color.White, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.width(24.dp)) // Espacio entre la imagen y el texto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 13.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(24.dp)) // Espacio entre el texto y la flecha
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Flecha para Detalles",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ServicesSection(navController: NavController?) {
    Column(modifier = Modifier.padding(16.dp)) {
        ServiceItem(
            title = "Asesoria Legal",
            description = "Consulta profesional donde se ofrece orientación en situaciones legales",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceItem(
            title = "Representacion Legal",
            description = "Actuación en nombre del cliente en procesos judiciales o negociaciones",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceItem(
            title = "Revisión de documentos legales",
            description = "Análisis detallado de contratos, acuerdos y otros documentos legales",
            navController = navController
        )
    }
}

@Composable
fun ServiceItem(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp) // Padding horizontal y vertical
            .clickable {
                navController?.navigate("servicios_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Cuadro más pequeño
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Img", color = Color.White, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.width(24.dp)) // Espacio entre la imagen y el texto
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 13.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(24.dp)) // Espacio entre el texto y la flecha
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Flecha para Detalles",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInfoCategoriasPreview() {
    PantallaInfoCategorias(navController = null)
}
