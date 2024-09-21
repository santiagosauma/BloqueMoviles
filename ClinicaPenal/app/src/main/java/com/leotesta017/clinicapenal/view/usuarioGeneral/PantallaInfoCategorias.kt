
package com.leotesta017.clinicapenal.view.usuarioGeneral


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import coil.compose.AsyncImage
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

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
fun CarruselDeNoticias(viewModel: VideoViewModel = viewModel()) {
    val videos by viewModel.videos.collectAsState()
    val error by viewModel.error.collectAsState()

    val totalPages = videos.size
    val pagerState = rememberPagerState { totalPages }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Noticias",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 10.dp, bottom = 8.dp)
        )

        if (error != null) {
            Text(text = error ?: "Error desconocido", color = Color.Red)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (totalPages > 0) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                    ) { page ->
                        val videoUrl = videos[page].url_video
                        val titulo = videos[page].titulo
                        val descripcion = videos[page].descripcion
                        Box(
                            modifier = Modifier
                                .fillMaxSize(0.90f)
                                .graphicsLayer {
                                    translationX = 50f
                                }

                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            // Mostrar el botón de reproducción con la descripción
                            VideoItemWithDescription(
                                videoUrl = videoUrl,
                                title = titulo,
                                description = descripcion
                            )
                        }
                    }
                } else {
                    Text(text = "No hay videos disponibles", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun VideoItemWithDescription(videoUrl: String, title: String, description: String) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // Cuadro del video con fondo negro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black, RoundedCornerShape(5.dp))
                .padding(5.dp)
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Reproducir Video",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el título
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Mostrar la descripción del video
        Text(
            text = description,
            fontSize = 13.sp,
            color = Color.Gray,
            maxLines = if (expanded) Int.MAX_VALUE else 3, // Controlar si se muestra todo el texto o no
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .clickable { expanded = !expanded } // Al hacer clic se expande o se contrae
                .padding(top = 8.dp)
        )

        // Texto "Ver más" o "Ver menos" para controlar la expansión
        Text(
            text = if (expanded) "Ver menos" else "Ver más",
            color = Color.Blue,
            fontSize = 13.sp,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(top = 4.dp)
        )
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
fun CategoriesSection(navController: NavController?, viewModel: CategoryViewModel = viewModel()) {
    // Obtenemos el estado actual de las categorías desde el ViewModel
    val categories by viewModel.categories.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Si hay un error, mostramos el mensaje de error
        if (error != null) {
            Text(text = error ?: "Error desconocido", color = Color.Red)
        } else {
            // Mostramos las categorías dinámicamente desde la base de datos
            categories.forEach { category ->
                CategoryItem(
                    title = category.titulo,
                    description = category.descripcion,
                    imageUrl = category.url_imagen,  // Pasamos la URL de la imagen
                    navController = navController
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(title: String, description: String, navController: NavController?, imageUrl: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate("detalle_info")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Mostrar la imagen desde la URL con AsyncImage (de Coil)
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray),
            contentScale = ContentScale.Crop
        )

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
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate("servicios_info")
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
fun PantallaInfoCategoriasPreview() {
    PantallaInfoCategorias(navController = null)
}
