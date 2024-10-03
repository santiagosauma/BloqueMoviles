@file:Suppress("DEPRECATION", "UNUSED_EXPRESSION")

package com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral

//VIEW MODEL
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.leotesta017.clinicapenal.model.CasosRepresentacion
import com.leotesta017.clinicapenal.model.Notificacion
import com.leotesta017.clinicapenal.model.SolicitudAdmin
import com.leotesta017.clinicapenal.model.SolicitudGeneral
import com.leotesta017.clinicapenal.view.videoPlayer.googleDrivePlayer.GoogleDriveVideoPlayer
import com.leotesta017.clinicapenal.view.videoPlayer.YouTubePlayerWithLifecycle
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.FullscreenActivity
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.FullscreenVideoActivity
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel

// ========================================
// ☰ SECCIÓN: Barras de Navegación/Visuales
// ========================================
@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Clínica Penal",
            fontSize = 38.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF303665)
        )
    }
}

@Composable
fun SearchBar(searchText: String)
{
    OutlinedTextField(
        value = searchText,
        onValueChange = {},
        label = { Text("Buscar...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        singleLine = true
    )
}


@Composable
fun GenericBottomBarItem(icon: ImageVector,
                         text: String,
                         isSelected: Boolean,
                         onClick: () -> Unit)
{
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = if (isSelected) Color(0xFF0B1F8C) else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) Color(0xFFFFFFFF) else Color.Black,
            )
        }
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun GenericBarraNav(navController: NavController?,
                    modifier: Modifier = Modifier,
                    destinations: List<String>,
                    icons: List<ImageVector>,
                    texts: List<String>)
{
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()?.value
    val currentDestination = currentBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        destinations.indices.forEach { index ->
            GenericBottomBarItem(
                icon = icons[index],
                text = texts[index],
                isSelected = currentDestination == destinations[index],
                onClick = { navController?.navigate(destinations[index]) }
            )
        }
    }
}





@Composable
fun RoundedButton(icon: ImageVector,
                  label: String,
                  onClick: () -> Unit)
{
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF1A237E))
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PantallasExtra(navController: NavController?,
                   routeJuribot: String,
                   routeCrearSolicitud: String)
{
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 25.dp, bottom = 100.dp)
    )
    {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedButton(
                icon = Icons.AutoMirrored.Filled.Chat,
                label = "JuriBot",
                onClick = { navController?.navigate(routeJuribot) }
            )
            RoundedButton(
                icon = Icons.Default.CalendarToday,
                label = "Solicitud de Cita",
                onClick = { navController?.navigate(routeCrearSolicitud) }
            )
        }
    }

}

@Composable
fun SpacedItem(spacing: Int,
               content: @Composable () -> Unit)
{
    content()
    Spacer(modifier = Modifier.height(spacing.dp))

}


@Composable
fun LabelCategoria(label: String,
                   modifier: Modifier = Modifier)
{
    Text(
        text = label,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier
    )
}


@Composable
fun LabelCategoriaConBoton(label: String,
                           navController: NavController?,
                           modifier: Modifier = Modifier,
                           navigateroute : String)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp)
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


//FUNCIONES PARA ITEMS DE NOTICIAS
@Composable
fun MyTextNoticias(text: String)
{
    Text(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(start = 15.dp, bottom = 8.dp)
    )
}

fun isYouTubeUrl(videoUrl: String): Boolean {
    return videoUrl.contains("youtube") || videoUrl.contains("youtu.be")
}

fun isGoogleDriveUrl(videoUrl: String): Boolean {
    return videoUrl.contains("drive.google.com")
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselDeNoticias(
    tipoPantalla: String? = null,
    viewModel: VideoViewModel = viewModel(),
    contentText: @Composable (() -> Unit)? = null,
) {
    val videos by viewModel.videos.collectAsState()
    val error by viewModel.error.collectAsState()

    // Filtrar los videos en base al tipo de pantalla
    val filteredVideos = if (tipoPantalla == "estudiante") {
        videos.filter { it.tipo == "estudiante" }
    } else {
        videos.filter { it.tipo != "estudiante" }
    }

    val totalPages = filteredVideos.size
    val pagerState = rememberPagerState { totalPages }

    // Obtenemos el contexto actual
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        contentText?.invoke()

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
                        val video = filteredVideos[page]
                        val isVisible = pagerState.currentPage == page

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                        ) {
                            VideoCard(
                                videoUrl = video.url_video,
                                title = video.titulo,
                                description = video.descripcion,
                                onFullscreenClick = { url ->
                                    if (isYouTubeUrl(url)) {
                                        val intent = Intent(context, FullscreenActivity::class.java)
                                        intent.putExtra("VIDEO_URL", url)
                                        context.startActivity(intent)
                                    } else if (isGoogleDriveUrl(url)) {

                                        val intent = Intent(context, FullscreenVideoActivity::class.java)
                                        intent.putExtra("VIDEO_URL", url)
                                        context.startActivity(intent)
                                    }
                                },
                                isVisible = isVisible,// Pasamos si el video es visible
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
fun VideoCard(
    videoUrl: String,
    title: String,
    description: String,
    onFullscreenClick: (String) -> Unit, // Callback para pantalla completa
    isVisible: Boolean, // Controlar si el video está visible
) {
    var expanded by remember { mutableStateOf(false) }
    val maxHeight = if (expanded) Int.MAX_VALUE.dp else 428.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .heightIn(min = 428.dp, max = maxHeight)
            .clip(RoundedCornerShape(16.dp)), // Aplicamos bordes redondeados a la tarjeta
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf0eee9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp) // Bordes redondeados para la tarjeta
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Cuadro del video y el botón en el mismo Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(275.dp)
                    .padding(bottom = 16.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(16.dp))
                ) {

                    if(isYouTubeUrl(videoUrl))  {
                        YouTubePlayerWithLifecycle(
                            videoUrl = videoUrl,
                            isVisible = isVisible,
                            onControllerVisibilityChanged = {false})
                    }

                    else if (isGoogleDriveUrl(videoUrl)) {
                        GoogleDriveVideoPlayer(
                            videoUrl = videoUrl,
                            isVisible = isVisible,
                            onControllerVisibilityChanged = {false}
                           )
                    }
                    else {
                        Text("URL no compatible", color = Color.Red)
                    }

                }

                // Botón para pantalla completa
                Button(
                    onClick = { onFullscreenClick(videoUrl) },
                    colors = ButtonDefaults.buttonColors(Color(0xFF0B1F8C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(16.dp))
                        .zIndex(1f) // Elevar el botón por encima del PlayerView

                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = "Pantalla Completa",
                            color = Color.White,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar el título
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Mostrar la descripción del video
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = if (expanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(top = 4.dp)
            )

            // Texto "Ver más" o "Ver menos" para controlar la expansión
            Text(
                text = if (expanded) "Ver menos" else "Ver más",
                color = Color(0xFF303665),
                fontSize = 13.sp,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(top = 4.dp)
            )
        }
    }
}




//FUNCIONES PARA ITEMS DE CATEGORIAS
@Composable
fun CategoriesSection(
    navController: NavController?,
    viewModel: CategoryViewModel = viewModel(),
    route: String
) {
    val categories by viewModel.categorias.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        when {
            categories.isEmpty() && error == null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            error != null -> {
                Text(text = error ?: "Error desconocido", color = Color.Red)
            }
            categories.isNotEmpty() -> {
                categories.forEach { category ->
                    CategoryItem(
                        title = category.titulo,
                        description = category.descripcion,
                        imageUrl = category.url_imagen, // Pasamos la URL de la imagen
                        navController = navController,
                        route = route,
                        categoriaId = category.id // Pasar el ID de la categoría
                    )
                }
            }
            else -> {
                Text(text = "No hay categorías disponibles", color = Color.Gray)
            }
        }
    }
}


@Composable
fun CategoryItem(
    title: String,
    description: String,
    navController: NavController?,
    imageUrl: String,
    route: String,
    categoriaId: String // Este ID es necesario para cargar la pantalla de detalles
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val encodedUrlImagen = Uri.encode(imageUrl)
                navController?.navigate("$route/$title/$description/$categoriaId/$encodedUrlImagen") // Navegar con el ID de la categoría
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf0eee9)), // Color de fondo personalizado para la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Elevación estándar para sombra
        shape = RoundedCornerShape(16.dp) // Bordes redondeados
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Espaciado interno de la tarjeta
        ) {
            // Imagen destacada en la parte superior con fondo gris
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = imageUrl, // Imagen de prueba
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Imagen más grande
                        .clip(RoundedCornerShape(8.dp)), // Bordes redondeados para la imagen
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre la imagen y el contenido

            // Contenido del título y la descripción
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                )
                {
                    // Título de la tarjeta
                    Text(
                        text = title,
                        fontSize = 18.sp, // Tamaño de texto para títulos
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // Color según el tema
                    )

                    Spacer(modifier = Modifier.height(4.dp)) // Espacio reducido entre el título y descripción

                    // Descripción de la tarjeta
                    Text(
                        text = description,
                        fontSize = 14.sp, // Tamaño de texto para contenido secundario
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Color secundario con opacidad
                        maxLines = 2, // Limitar a dos líneas
                        overflow = TextOverflow.Ellipsis // Cortar el texto si es necesario
                    )
                }
            }
        }
    }
}


//FUNCIONES PARA ITEMS DE SERVICIO
@Composable
fun ServicesSection(
    navController: NavController?,
    viewModel: ServicioViewModel = viewModel(),
    route: String
) {
    val servicios by viewModel.servicios.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(4.dp)) {
        when {
            servicios.isEmpty() && error == null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            error != null -> {
                Text(text = error ?: "Error desconocido", color = Color.Red)
            }
            servicios.isNotEmpty() -> {
                servicios.forEach { servicio ->
                    ServiceItem(
                        title = servicio.titulo,
                        description = servicio.descripcion,
                        imageUrl = servicio.url_imagen,
                        navController = navController,
                        route = route,
                        servicioId = servicio.id // Pasar el ID del servicio
                    )
                }
            }
            else -> {
                Text(text = "No hay servicios disponibles", color = Color.Gray)
            }
        }
    }
}



@Composable
fun ServiceItem(
    title: String,
    description: String,
    navController: NavController?,
    imageUrl: String,
    route: String,
    servicioId: String // Este ID es necesario para cargar la pantalla de detalles
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val encodedUrlImagen = Uri.encode(imageUrl)
                navController?.navigate("$route/$title/$description/$servicioId/$encodedUrlImagen") // Navegar con el ID de la categoría
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf0eee9)), // Color de fondo personalizado para la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Elevación estándar para sombra
        shape = RoundedCornerShape(16.dp) // Bordes redondeados
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Espaciado interno de la tarjeta
        ) {
            // Imagen destacada en la parte superior con fondo gris
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = imageUrl, // Imagen de prueba
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Imagen más grande
                        .clip(RoundedCornerShape(8.dp)), // Bordes redondeados para la imagen
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre la imagen y el contenido

            // Contenido del título y la descripción
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                )
                {
                    // Título de la tarjeta
                    Text(
                        text = title,
                        fontSize = 18.sp, // Tamaño de texto para títulos
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // Color según el tema
                    )

                    Spacer(modifier = Modifier.height(4.dp)) // Espacio reducido entre el título y descripción

                    // Descripción de la tarjeta
                    Text(
                        text = description,
                        fontSize = 14.sp, // Tamaño de texto para contenido secundario
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Color secundario con opacidad
                        maxLines = 2, // Limitar a dos líneas
                        overflow = TextOverflow.Ellipsis // Cortar el texto si es necesario
                    )
                }
            }
        }
    }
}



//FUNCIONES PARA LA PANTALLA DE DETALLE
@Composable
fun HeaderSection(title: String, navController: NavController?) {
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
                fontSize = 25.sp,
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
fun SectionTitle(title: String) {
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
fun SectionContent(content: String) {
    Text(
        text = content,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}


//FUNCIONES PARA LA PANTALLA DE INFO DE LA CLINICA
@Composable
fun SeccionNosotros(title: String, description: String) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SeccionHorarios(title: String, schedule: String) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = schedule,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun SeccionDirecciones(title: String, addresses: List<String>) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            addresses.forEach { address ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("• ")
                        }
                        append(address)
                    },
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SeccionContacto(title: String, phone: String, email: String) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Teléfono: ")
                    }
                    append(phone)
                },
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("• Correo: ")
                    }
                    append(email)
                },
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun RedesSociales(title: String, icons: List<Int>, onIconClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(350.dp)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                icons.forEach { icon ->
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onIconClick(icon) }
                    )
                }
            }
        }
    }
}

@Composable
fun Calendarios(title: String, events: List<Pair<String, String>>) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(10.dp))
            events.forEach { event ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("• ${event.first}: ")
                        }
                        append(event.second)
                    },
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}


//FUNCIONES PARA PANTALLA DE MOSTRAR SOLICITUDES
@Composable
fun ItemTemplate(
    id: String,
    titulo: String,
    detalles: List<String>,
    estado: String,
    estadoColor: Color,
    navController: NavController?,
    navRoute: String,
    onDelete: (String) -> Unit,
    confirmDeleteText: String
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp)
            .clickable {
                navController?.navigate(navRoute)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                 Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = titulo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    detalles.forEach { detalle ->
                        Text(
                            text = detalle,
                            fontSize = 14.5.sp
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            text = estado,
                            color = Color.Black,
                            fontSize = 14.5.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(estadoColor, shape = CircleShape)
                        )
                    }
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones",
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                showDialog = true
                            },
                            text = { Text("Eliminar") }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(id)
                    showDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text(confirmDeleteText) }
        )
    }
}

@Composable
fun SolicitudAdminItem(
    solicitud: SolicitudAdmin,
    navController: NavController?,
    onDelete: (String) -> Unit,
    route: String
) {
    ItemTemplate(
        id = solicitud.id,
        titulo = "${solicitud.id} - ${solicitud.titulo}",
        detalles = listOf(solicitud.fechaRealizada, solicitud.nombreUsuario),
        estado = solicitud.estado,
        estadoColor = solicitud.estadoColor,
        navController = navController,
        navRoute = route,
        onDelete = onDelete,
        confirmDeleteText = "¿Está seguro de que desea eliminar esta solicitud?"
    )
}

@Composable
fun CasoRepresentacionItem(
    casosRepresentacion: CasosRepresentacion,
    navController: NavController?,
    onDelete: (String) -> Unit,
    route: String
) {
    ItemTemplate(
        id = casosRepresentacion.id,
        titulo = "Caso: ${casosRepresentacion.id} - ${casosRepresentacion.tipo}",
        detalles = listOf("Fecha: ${casosRepresentacion.fechaRealizada}", "Usuario Asignado: ${casosRepresentacion.usuarioAsignado}"),
        estado = casosRepresentacion.estado,
        estadoColor = casosRepresentacion.estadoColor,
        navController = navController,
        navRoute = route,
        onDelete = onDelete,
        confirmDeleteText = "¿Está seguro de que desea eliminar este caso de representación?"
    )
}


@Composable
fun GeneralItemTemplate(
    title: String,
    details: List<String>,
    estado: String? = null,
    estadoColor: Color? = null,
    isImportant: Boolean = false,
    icon: ImageVector? = null,  // Hacemos el ícono opcional
    extraAction: (@Composable () -> Unit)? = null,
    navController: NavController? = null,
    linkText: String? = null,
    linkRoute: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.dp,
                if (isImportant) Color(0xFF303665) else Color.Transparent,
                RoundedCornerShape(16.dp)
            )
            .shadow(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E4E4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Solo mostrar el ícono si no es nulo
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "Icono",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                details.forEach { detail ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = detail,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                estado?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = estadoColor ?: Color.Black,
                        fontSize = 14.sp
                    )
                }
                linkText?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = Color(0xFF303665),
                        modifier = Modifier.clickable { linkRoute?.let { navController?.navigate(it) } }
                    )
                }
            }

            extraAction?.invoke() // Aquí se muestra el menú extra si se proporciona
        }
    }
}




@Composable
fun SolicitudGeneralItem(
    solicitud: SolicitudGeneral,
    navController: NavController?,
    valorarRoute: String
) {
    GeneralItemTemplate(
        title = "Caso ${solicitud.id}",
        details = listOf(solicitud.fechaRealizada, solicitud.proximaCita),
        estado = solicitud.estado,
        estadoColor = solicitud.estadoColor,
        navController = navController,
        extraAction = {
            if (solicitud.estado == "Finalizado") {
                var expanded by remember { mutableStateOf(false) }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Más opciones",
                        tint = Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            navController?.navigate(route = valorarRoute)
                        },
                        text = { Text("Valorar") }
                    )
                }
            }
        }
    )
}


@Composable
fun NotificacionItem(
    notificacion: Notificacion,
    navController: NavController?
) {
    GeneralItemTemplate(
        title = notificacion.titulo,
        details = listOf(notificacion.fecha, notificacion.detalle),
        isImportant = notificacion.isImportant,
        icon = Icons.Default.Notifications,
        linkText = notificacion.enlace,
        linkRoute = notificacion.rutaEnlace,
        navController = navController
    )
}


// FUNCIONES PARA PANTALLAS MODIFICAR INFO/SERVICIOS ADMIN



@Composable
fun TextEditor(
    initialText: String = "",
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    applyStyle: (TextStyle) -> TextStyle = { it }
) {
    var text by remember { mutableStateOf(initialText) }

    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .fillMaxWidth()
            .height(300.dp) // Ajusta la altura según tus necesidades
            .verticalScroll(rememberScrollState())
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onTextChange(it)
            },
            textStyle = applyStyle(TextStyle.Default).copy(color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}


@Composable
fun ApplyStyleButtons(
    onApplyBold: () -> Unit,
    onApplyItalic: () -> Unit,
    onApplyUnderline: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = onApplyBold) {
            Icon(imageVector = Icons.Default.FormatBold, contentDescription = "Negrita")
        }
        IconButton(onClick = onApplyItalic) {
            Icon(imageVector = Icons.Default.FormatItalic, contentDescription = "Cursiva")
        }
        IconButton(onClick = onApplyUnderline) {
            Icon(imageVector = Icons.Default.FormatUnderlined, contentDescription = "Subrayado")
        }
    }
}

//MARKDOWN PANTALLA DETALLE
fun preprocesarMarkdown(markdown: String): String {
    return markdown
        .replace("####", "\n####")
        .replace("###", "\n###")
        .replace("- ", "\n• ") // Cambiar guiones por bolitas
        .replace("\n\n", "\n")
}

@Composable
fun CustomMarkdownText(content: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 0.dp)) {
        val lines = content.split("\n")
        for (line in lines) {
            when {
                line.startsWith("####") -> {
                    SectionTitle(
                        title = line.removePrefix("####").trim()
                    )
                    Spacer(modifier = Modifier.height(6.dp))  // Reducir el espacio aquí
                }
                line.startsWith("###") -> {
                    Spacer(modifier = Modifier.height(12.dp))  // Reducir el espacio aquí
                    Text(
                        text = line.removePrefix("###").trim(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                            .padding(16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))  // Reducir el espacio aquí
                }
                else -> {
                    Text(
                        text = parseMarkdownToAnnotatedString(line),
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))  // Reducir el espacio aquí
                }
            }
        }
    }
}

@Composable
fun parseMarkdownToAnnotatedString(text: String): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val boldRegex = Regex("""\*\*(.*?)\*\*""")
        boldRegex.findAll(text).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last
            append(text.substring(currentIndex, start))
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(matchResult.groupValues[1])
            }
            currentIndex = end + 1
        }
        append(text.substring(currentIndex, text.length))
    }
}

