package com.leotesta017.clinicapenal.usuarioEstudiante

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar

@Composable
fun PantallaInfoEstudiante(navController: NavController?) {
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
                    modifier = Modifier.padding(0.dp),
                    navigateroute = "modificar-info"
                )
                Spacer(modifier = Modifier.height(16.dp))
                CarruselDeNoticias(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoriaConBoton(
                    label = "Información Legal",
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                    navigateroute = "modificar-info"
                )
                CategoriesSectionModificableEstudiante(navController)
                Spacer(modifier = Modifier.height(16.dp))
                LabelCategoriaConBoton(
                    label = "Servicios",
                    navController = navController,
                    modifier = Modifier.padding(0.dp),
                    navigateroute = "modificar-info"
                )
                ServicesSectionModificableEstudiante(navController)
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
                onClick = { navController?.navigate("JuriBotAdmin") }
            )
            RoundedButton(
                icon = Icons.Default.CalendarToday,
                label = "Solicitud de Cita",
                onClick = { navController?.navigate("crearsolicitud") }
            )
        }

        EstudiantesBarraNav(
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
            .padding(end = 0.dp, start = 32.dp)
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
fun LabelCategoriaConBoton(label: String, navController: NavController?, modifier: Modifier = Modifier, navigateroute : String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 14.dp, start = 10.dp)
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { navController?.navigate(navigateroute) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B1F8C),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .height(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Agregar"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Agregar")
        }
    }
}

@Composable
fun CategoriesSectionModificableEstudiante(navController: NavController?) {
    Column(modifier = Modifier.padding(16.dp)) {
        CategoryItemModificableEstudiante(
            title = "Violencia Doméstica",
            description = "Maltrato físico o emocional dentro del ámbito familiar",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItemModificableEstudiante(
            title = "Adeudo",
            description = "Falta de pago de una deuda o compromiso financiero.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItemModificableEstudiante(
            title = "Vehicular",
            description = "Infracción de delito relacionado con el uso indebido de vehículos.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItemModificableEstudiante(
            title = "Robo y hurto",
            description = "Tomar algo ajeno sin permiso, con intención de no devolverlo.",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        CategoryItemModificableEstudiante(
            title = "Extorsión y Amenaza",
            description = "Uso de amenazas o coerción para obtener dinero o bienes mediante intimidación.",
            navController = navController
        )
    }
}

@Composable
fun CategoryItemModificableEstudiante(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate("modificar-info-estudiante")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Img", color = Color.White, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 13.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(24.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Flecha para Detalles",
            tint = Color.Blue,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ServicesSectionModificableEstudiante(navController: NavController?) {
    Column(modifier = Modifier.padding(16.dp)) {
        ServiceItemModificableEstudiante(
            title = "Asesoria Legal",
            description = "Consulta profesional donde se ofrece orientación en situaciones legales",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceItemModificableEstudiante(
            title = "Representacion Legal",
            description = "Actuación en nombre del cliente en procesos judiciales o negociaciones",
            navController = navController
        )
        Spacer(modifier = Modifier.height(12.dp))
        ServiceItemModificableEstudiante(
            title = "Revisión de documentos legales",
            description = "Análisis detallado de contratos, acuerdos y otros documentos legales",
            navController = navController
        )
    }
}

@Composable
fun ServiceItemModificableEstudiante(title: String, description: String, navController: NavController?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate("modificar-info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Img", color = Color.White, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 13.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(24.dp))
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
fun PreviewPantallaInfoEstudiante() {
    PantallaInfoEstudiante(navController = null)
}
