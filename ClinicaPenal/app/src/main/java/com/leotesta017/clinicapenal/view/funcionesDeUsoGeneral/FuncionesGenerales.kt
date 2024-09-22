package com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.currentBackStackEntryAsState

//VIEW MODEL
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel


//FUNCION PARA IMPLEMENTAR UN TOP BAR CON EL NOMBRE DE LA CLINICA PENAL
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
            color = Color.Blue
        )
    }
}

//FUNCION PARA IMPLEMENTAR UN SEARCH BAR EN VARIAS VISTAS
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

//FUNCION PARA LA PARTE DE JURIBOT Y HACER SOLICITUDES
@Composable
fun PantallasExtra(navController: NavController?,
                   routeJuribot: String,
                   routeCrearSolicitud: String)
{
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
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
fun SpacedItem(spacing: Dp = 16.dp,
               content: @Composable () -> Unit)
{
    content()
    Spacer(modifier = Modifier.height(spacing))

}


//FUNCIONES PARA LA VISTA DE INFORMACION DE NOTICIAS/SERVICIOS/CATEGORIAS
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
            .padding(start = 10.dp, bottom = 8.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselDeNoticias(viewModel: VideoViewModel = viewModel(),
                       contentText: @Composable (() -> Unit)? = null)
{
    val videos by viewModel.videos.collectAsState()
    val error by viewModel.error.collectAsState()

    val totalPages = videos.size
    val pagerState = rememberPagerState { totalPages }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
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
fun VideoItemWithDescription(videoUrl: String,
                             title: String,
                             description: String)
{
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


//FUNCIONES PARA ITEMS DE CATEGORIAS
@Composable
fun CategoriesSection(navController: NavController?,
                      viewModel: CategoryViewModel = viewModel(),
                      route: String)
{
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
                    navController = navController,
                    route =  route
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CategoryItem(title: String,
                 description: String,
                 navController: NavController?,
                 imageUrl: String, route: String)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate(route)
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


//FUNCIONES PARA ITEMS DE SERVICIO
@Composable
fun ServicesSection(navController: NavController?,
                    viewModel: ServicioViewModel = viewModel(),
                    route: String)
{
    // Obtenemos el estado actual de los servicios desde el ViewModel
    val servicios by viewModel.servicios.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // Si hay un error, mostramos el mensaje de error
        if (error != null) {
            Text(text = error ?: "Error desconocido", color = Color.Red)
        } else {
            // Mostramos los servicios dinámicamente desde la base de datos
            servicios.forEach { servicio ->
                ServiceItem(
                    title = servicio.titulo,
                    description = servicio.descripcion,
                    imageUrl = servicio.url_imagen,  // Pasamos la URL de la imagen
                    navController = navController,
                    route = route
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ServiceItem(title: String,
                description: String,
                navController: NavController?,
                imageUrl: String,
                route: String)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                navController?.navigate(route)
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



//BARRA DE NAVEGACION
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



