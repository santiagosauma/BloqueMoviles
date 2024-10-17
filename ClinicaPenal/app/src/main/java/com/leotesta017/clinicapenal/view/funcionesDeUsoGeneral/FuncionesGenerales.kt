
package com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral

//VIEW MODEL
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.model.Evento
import com.leotesta017.clinicapenal.model.Servicio
import com.leotesta017.clinicapenal.model.Video
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.FullscreenActivity
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.FullscreenVideoActivity
import com.leotesta017.clinicapenal.view.videoPlayer.googleDrivePlayer.GoogleDriveVideoPlayer
import com.leotesta017.clinicapenal.view.videoPlayer.youtubeVideoPlayer.YouTubePlayerWithLifecycle
import com.leotesta017.clinicapenal.viewmodel.VideoViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.Case_CounterViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
fun SearchBarHistorialSolicitudes(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    casos: List<Triple<Case, String, Boolean>>,
    isAdmin: Boolean,
    isUsuarioGeneral: Boolean,
    navController: NavController?,
    caseCounterViewModel: Case_CounterViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    caseViewModel: CaseViewModel = viewModel(),
    onSearchStarted: (Boolean) -> Unit
) {
    var search by remember { mutableStateOf(searchText) }

    val dayTranslations = mapOf(
        "Lunes" to "Mon",
        "Martes" to "Tue",
        "Miércoles" to "Wed",
        "Jueves" to "Thu",
        "Viernes" to "Fri",
        "Sábado" to "Sat",
        "Domingo" to "Sun"
    )

    OutlinedTextField(
        value = search,
        onValueChange = {
            search = it
            onSearchTextChange(it)
            onSearchStarted(it.isNotBlank())  // Indica que la búsqueda ha comenzado si no está en blanco
        },
        label = { Text("Buscar por número de caso o nombre del usuario...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        singleLine = true
    )

    val caseCounters = remember { mutableStateMapOf<String, Int?>() }

    val caseUserNames = remember { mutableStateMapOf<String, String?>() }

    casos.forEach { case ->
        val caseId = case.first.case_id

        // Obtener el caseCounter
        if (caseCounters[caseId] == null) {
            LaunchedEffect(caseId) {
                val caseCounter = caseCounterViewModel.findOrAddCase(caseId)
                caseCounters[caseId] = caseCounter
            }
        }

        if (caseUserNames[caseId] == null) {
            LaunchedEffect(caseId) {
                val generalUserName = usuarioViewModel.fetchUserByNameCaseId(caseId,"general")
                caseUserNames[caseId] = generalUserName
            }
        }
    }

    if (search.isBlank()) {
        return
    }

    val filteredCases = remember(search) {
        val translatedSearch = dayTranslations[search.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }] ?: search

        casos.filter { case ->
            val caseCounter = caseCounters[case.first.case_id]
            val userName = caseUserNames[case.first.case_id] ?: ""

            val isCaseIdMatch = caseCounter.toString() == search

            val isUserNameMatch = userName.contains(search, ignoreCase = true)

            val isDateMatch = case.second.take(3).contains(translatedSearch, ignoreCase = true)

            isCaseIdMatch || isUserNameMatch || isDateMatch
        }
    }

    if (filteredCases.isNotEmpty()) {
        filteredCases.forEach { case ->
            if(!isUsuarioGeneral){
                CaseUserAdminItem(
                    case = case,
                    onDiscard = { id ->
                        if (isAdmin) {
                            caseViewModel.discardCase(id)
                        }
                    },
                    confirmDeleteText = "¿Estás seguro de que deseas descartar este caso?",
                    navController = navController,
                    route = if (isAdmin) {
                        "actualizarcasos"
                    } else "detallecasoestudiante",
                    isAdmin = isAdmin,
                    onDelete = { id ->
                        if (isAdmin) {
                            caseViewModel.deleteCase(id)
                        }
                    },
                )
            }
            else{
                CaseUserGenaralItem(
                    case = case,
                    navController = navController
                )
            }

        }
    } else if (search.isNotBlank()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = "Sin resultados",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "No se encontraron casos",
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
        }
    }
}



@Composable
fun SearchBarPantallaInfo(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    categorias: List<Categoria>? = null,
    servicios: List<Servicio>? = null,
    errorCategoria: String? = "",
    errorServicio: String? = "",
    navController: NavController?,
    routeCategoria: String,
    routeServicio: String,
    onSearchStarted: (Boolean) -> Unit
) {
    var search by remember { mutableStateOf(searchText) }

    OutlinedTextField(
        value = search,
        onValueChange = {
            search = it
            onSearchTextChange(it)
            onSearchStarted(it.isNotBlank())
        },
        label = { Text("Buscar...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
        singleLine = true
    )

    var resServicio = false
    var resCategoria = false


    if (search.isNotBlank() && !categorias.isNullOrEmpty()) {
        val filteredCategorias = categorias.filter { it.titulo.contains(search, ignoreCase = true) }
        if (filteredCategorias.isNotEmpty()) {
            filteredCategorias.forEach { categoria ->
                CategoryItem(
                    title = categoria.titulo,
                    description = categoria.descripcion,
                    imageUrl = categoria.url_imagen,
                    navController = navController,
                    route = routeCategoria,
                    categoriaId = categoria.id
                )
            }
        }
        else{
            resCategoria = true
        }
    }

    // Mostrar servicios que coinciden con la búsqueda
    if (search.isNotBlank() && !servicios.isNullOrEmpty()) {
        val filteredServicios = servicios.filter { it.titulo.contains(search, ignoreCase = true) }
        if (filteredServicios.isNotEmpty()) {
            filteredServicios.forEach { servicio ->
                ServiceItem(
                    title = servicio.titulo,
                    description = servicio.descripcion,
                    imageUrl = servicio.url_imagen,
                    navController = navController,
                    route = routeServicio,
                    servicioId = servicio.id
                )
            }
        }
        else{
            resServicio = true
        }
    }
    SpacedItem(spacing = 32) {

    }
    if (search.isNotBlank() && resServicio && resCategoria) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = "Sin resultados",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            LabelCategoria(
                label = "No se encontró información relacionada",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    if (!errorCategoria.isNullOrEmpty()) {
        Text(text = errorCategoria, color = Color.Red)
    }

    if (!errorServicio.isNullOrEmpty()) {
        Text(text = errorServicio, color = Color.Red)
    }
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
                    color = if (isSelected) Color(0xFF002366) else Color.Transparent,
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
fun GenericBarraNav(
    navController: NavController?,
    modifier: Modifier = Modifier,
    destinations: List<String>,
    icons: List<ImageVector>,
    texts: List<String>
) {
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()?.value
    val currentDestination = currentBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
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
            .background(Color(0xFF002366))
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
fun RoundedButton2(icon: ImageVector,
                  label: String,
                  onClick: () -> Unit)
{
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF002366))
            .padding(start = 18.dp,top = 10.dp, bottom = 10.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
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
                containerColor = Color(0xFF002366),
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
            .padding(bottom = 8.dp)
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
    navController: NavController,
    viewModel: VideoViewModel = viewModel(),
    userModel: UsuarioViewModel = viewModel(),
    contentText: @Composable (() -> Unit)? = null,
) {
    val videos by viewModel.videos.collectAsState()
    val error by viewModel.error.collectAsState()

    val tipousuario by userModel.userType.collectAsState()

    LaunchedEffect(Unit) {
        val userid = UserIdData.userId
        if (userid != null) {
            userModel.fetchUserType(userid)
        }
    }

    val filteredVideos = when (tipousuario) {
        "estudiante" -> videos.filter { it.tipo == "estudiante" }
        "general" -> videos.filter { it.tipo == "general" }
        "colaborador" -> videos.filter { it.tipo == "estudiante" || it.tipo == "general" }
        "administrador" -> videos // Administrador can see all videos
        else -> emptyList()
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
                                video = video,
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
                                isVisible = isVisible,
                                showExtraButtons = tipousuario == "administrador" || tipousuario == "colaborador",
                                onEditClick = {
                                    // Navigate to the edit screen, passing necessary data
                                    navController.navigate("editar_video/${video.id}")
                                },
                                onDeleteClick = {
                                    viewModel.deleteVideo(video.id)
                                    navController.navigate("pantallainfoadmin")
                                }
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
    video: Video,
    onFullscreenClick: (String) -> Unit,
    isVisible: Boolean,
    showExtraButtons: Boolean = false,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val maxHeight = if (expanded) Int.MAX_VALUE.dp else 428.dp
    var showDialog by remember { mutableStateOf(false)}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .heightIn(min = 428.dp, max = maxHeight)
            .clip(RoundedCornerShape(16.dp)),
        colors = cardColors(containerColor = Color(0xFFf0eee9)),
        elevation = cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .animateContentSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenWidthDp * 10 / 16).dp)
                    .padding(bottom = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(bottom = 40.dp)
                ) {
                    if (isYouTubeUrl(video.url_video)) {
                        YouTubePlayerWithLifecycle(
                            videoUrl = video.url_video,
                            isVisible = isVisible,
                            onControllerVisibilityChanged = { false })
                    } else if (isGoogleDriveUrl(video.url_video)) {
                        GoogleDriveVideoPlayer(
                            videoUrl = video.url_video,
                            isVisible = isVisible,
                            onControllerVisibilityChanged = { false }
                        )
                    } else {
                        Text("URL no compatible", color = Color.Red)
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .zIndex(1f)
                        .height(38.dp)
                        .clip(RectangleShape)
                        .background(Color(0xFF002366))
                        .clickable {
                            onFullscreenClick(video.url_video)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Pantalla Completa",
                        color = Color.White,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = video.titulo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = video.descripcion,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = if (expanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(top = 4.dp)
                    .animateContentSize()
            )

            Text(
                text = if (expanded) "Ver menos" else "Ver más",
                color = Color(0xFF303665),
                fontSize = 13.sp,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(top = 4.dp)
            )

            if (showExtraButtons && expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onEditClick?.invoke() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF002366),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Editar Video")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        showDialog = true
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF002366),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Borrar Video")
                }
            }

            if (showDialog)
            {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteClick?.invoke()
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
                    title = { Text("Confirmar Eliminar Video") },
                    text = { Text("¿Estas seguro de que quieres eliminar este video?") }
                )
            }
        }
    }
}




// FUNCIONES PARA ITEMS DE CATEGORIAS
@Composable
fun CategoriesSection(
    navController: NavController?,
    route: String,
    categories: List<Categoria>,
    error: String?
) {
    val loading = categories.isEmpty() && error == null

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        if (categories.isNotEmpty()) {
            categories.forEach { category ->
                CategoryItem(
                    title = category.titulo,
                    description = category.descripcion,
                    imageUrl = category.url_imagen,
                    navController = navController,
                    route = route,
                    categoriaId = category.id
                )
            }
        } else if (error != null) {
            Text(text = error, color = Color.Red, modifier = Modifier.padding(horizontal = 16.dp))
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (!loading && categories.isEmpty()) {
            Text(text = "No hay categorías disponibles", color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))
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
    categoriaId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp)
            .clickable {
                val encodedUrlImagen = Uri.encode(imageUrl)
                navController?.navigate("$route/$title/$description/$categoriaId/$encodedUrlImagen")
            },
        colors = cardColors(containerColor = Color(0xFFf0eee9)),
        elevation = cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// FUNCIONES PARA ITEMS DE SERVICIO
@Composable
fun ServicesSection(
    navController: NavController?,
    route: String,
    servicios: List<Servicio>,
    error: String?
) {
    val loading = servicios.isEmpty() && error == null

    Column(modifier = Modifier) {
        if (servicios.isNotEmpty()) {
            servicios.forEach { servicio ->
                ServiceItem(
                    title = servicio.titulo,
                    description = servicio.descripcion,
                    imageUrl = servicio.url_imagen,
                    navController = navController,
                    route = route,
                    servicioId = servicio.id
                )
            }
        } else if (error != null) {
            Text(text = error, color = Color.Red, modifier = Modifier)
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (!loading && servicios.isEmpty()) {
            Text(text = "No hay servicios disponibles", color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))
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
    servicioId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val encodedUrlImagen = Uri.encode(imageUrl)
                navController?.navigate("$route/$title/$description/$servicioId/$encodedUrlImagen")
            },
        colors = cardColors(containerColor = Color(0xFFf0eee9)),
        elevation = cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// FUNCIONES PARA LA PANTALLA DE DETALLE
@Composable
fun HeaderSection(title: String, navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
            .background(Color.White)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@Composable
fun SectionTitle2(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

@Composable
fun SectionContent(content: String) {
    Text(
        text = content,
        fontSize = 16.sp,
        color = Color.Black
    )
}



//FUNCIONES PARA LA PANTALLA DE INFO DE LA CLINICA

@Composable
fun SeccionNosotros(title: String, description: String) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SeccionHorarios(title: String, schedule: String) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = schedule,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SeccionDirecciones(title: String, addresses: List<String>) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            addresses.forEach { address ->
                Text(
                    text = address,
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SeccionContacto(title: String, phone: String, email: String) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "• Teléfono: $phone",
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "• Correo: $email",
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RedesSociales(title: String, icons: List<Int>, onIconClick: (Int) -> Unit) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                icons.forEach { icon ->
                    IconButton(onClick = { onIconClick(icon) }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

val estiloCaja = Modifier
    .padding(horizontal = 16.dp)
    .fillMaxWidth()
    .border(
        BorderStroke(2.dp, Color(0xFF002366)),
        shape = RoundedCornerShape(16.dp)
    )
    .padding(16.dp)

@Composable
fun Calendarios(
    title: String,
    eventos: List<Evento>,
    isCollaborator: Boolean,
    onDeleteEvento: (Evento) -> Unit
) {
    Box(
        modifier = estiloCaja
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                color = Color(0xFF002366),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            val sdf = SimpleDateFormat("dd 'de' MMMM (HH:mm)", Locale("es", "ES"))
            eventos.forEach { evento ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color(0xFF002366))) {
                                append(sdf.format(evento.fecha))
                            }
                            append(" : ${evento.titulo} - ${evento.lugar}")
                        },
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    if (isCollaborator) {
                        IconButton(onClick = { onDeleteEvento(evento) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar evento",
                                tint = Color.Black // Color del botón cambiado a negro
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}



//FUNCIONES PARA PANTALLA DE MOSTRAR SOLICITUDES
@Composable
fun CaseUserAdminItem(
    case: Triple<Case, String, Boolean>,
    onDiscard: (String) -> Unit,
    onDelete:  (String) -> Unit,
    confirmDeleteText: String,
    caseCounterViewModel: Case_CounterViewModel = viewModel(),
    navController: NavController?,
    route: String,
    isAdmin: Boolean,
    usuarioViewModel: UsuarioViewModel = viewModel(),
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var caseCounter by remember { mutableStateOf<Int?>(null) }
    var username by remember { mutableStateOf<String?>(null)}

    // Ejecutamos el LaunchedEffect solo para este caseId
    LaunchedEffect(case.first.case_id) {
        val index = caseCounterViewModel.findOrAddCase(case.first.case_id)
        caseCounter = index
    }

    LaunchedEffect(case.first.case_id) {
        val generalUserName = usuarioViewModel.fetchUserByNameCaseId(case.first.case_id,"general")
        username = generalUserName
    }

    val caseViewModel: CaseViewModel = viewModel()

    val ultimaCita by caseViewModel.lastAppointment.collectAsState()

    LaunchedEffect(Unit) {
        caseViewModel.fetchLastAppointment(case.first.case_id)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp),
        colors = cardColors(
            containerColor = Color(0xFFf0eee9)
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
                        text = "Caso: ID-$caseCounter",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    Text(text = "$username", fontSize = 14.sp)
                    Text(text = "Lugar: ${case.first.place}", fontSize = 14.sp)
                }
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 10.dp, y = 10.dp)
                    ) {
                        // Si el usuario es admin, mostrar opción para eliminar
                        if (isAdmin) {
                            if(case.first.situation != "Caso descartado")
                            {
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        showDialog = true
                                    },
                                    text = { Text("Descartar") }
                                )
                            }
                            else if(case.first.situation == "Caso descartado")
                            {
                                DropdownMenuItem(
                                    onClick = {
                                        expanded = false
                                        showDeleteDialog = true
                                    },
                                    text = { Text("Eliminar") }
                                )
                            }
                        }

                        // Opción de ver detalles, común a ambos roles
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                navController?.navigate("$route/${case.first.case_id}")
                            },
                            text = { Text("Detalles") }
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Última cita agendada:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    if (case.second.isNotEmpty()) {

                        val lastAppointment = case.second

                        // Fecha de la última cita
                        Text(
                            text = "Fecha: $lastAppointment",
                            fontSize = 14.sp,
                            color = if (case.first.suspended || (ultimaCita?.suspended == true)) Color.Red else Color.Blue
                        )

                        // Estado de confirmación
                        Text(
                            text = "Cita Confirmada: ${if (case.third) "Sí" else "No"}",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Cita Suspendida: ${if (ultimaCita?.suspended == true) "Sí" else "No"}",
                            fontSize = 14.sp
                        )
                    } else {
                        Text(text = "Sin citas disponibles", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 17.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estado: ${case.first.state}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(end = 8.dp)  // Espacio entre el texto y el círculo
                        )

                        // Círculo de color según el estado
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(
                                    when (case.first.state) {
                                        "Activo" -> Color.Yellow
                                        "Finalizado" -> Color.Green
                                        "Suspendido" -> Color.Red
                                        else -> Color.Gray
                                    }
                                )
                                .border(1.dp, Color.Black, CircleShape)
                        )
                    }
                }
            }
        }
    }

    if (showDialog && isAdmin) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDiscard(case.first.case_id)
                    showDialog = false
                    navController?.navigate("generalsolicitudadmin")
                }) {
                    Text("Descartar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Descartar Caso") },
            text = { Text("¿Estas seguro que quieres descartar este caso?") }
        )
    }
    if (showDeleteDialog && isAdmin) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(case.first.case_id)
                    showDeleteDialog = false
                    navController?.navigate("generalsolicitudadmin")
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar Eliminar Caso") },
            text = { Text(confirmDeleteText) }
        )
    }
}

@Composable
fun CaseUserGenaralItem(
    case: Triple<Case, String, Boolean>,
    caseCounterViewModel: Case_CounterViewModel = viewModel(),
    navController: NavController?
) {
    var expanded by remember { mutableStateOf(false) }
    var caseCounter by remember { mutableStateOf<Int?>(null) }

    // Ejecutamos el LaunchedEffect solo para este caseId
    LaunchedEffect(case.first.case_id) {
        val index = caseCounterViewModel.findOrAddCase(case.first.case_id)
        caseCounter = index
    }

    val caseViewModel: CaseViewModel = viewModel()

    val ultimaCita by caseViewModel.lastAppointment.collectAsState()

    LaunchedEffect(Unit) {
        caseViewModel.fetchLastAppointment(case.first.case_id)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(1.dp),
        colors = cardColors(containerColor = Color(0xFFf0eee9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Caso: ID-$caseCounter",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.5.sp
                    )
                    Text(text = "Lugar: ${case.first.place}", fontSize = 14.sp)
                }

                Box{
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Más opciones")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 10.dp, y = 10.dp)
                    ) {
                        var hasOptions = false

                        if(case.first.represented && !case.first.segundoFormulario)
                        {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    navController?.navigate("SegundoFormulario/${case.first.case_id}")
                                },
                                text = { Text("Segundo Formulario") }
                            )
                            hasOptions = true
                        }

                        val currentDate = Date()

                        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
                        val parsedCaseDate: Date? = try {
                            dateFormat.parse(case.second)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }



                        val rating = ultimaCita?.valoration
                        
                        if (parsedCaseDate != null && parsedCaseDate.before(currentDate) && rating == 0) {

                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    navController?.navigate("ReviewComentarios/${case.first.case_id}")
                                },
                                text = { Text("Valorar Cita") }
                            )
                            hasOptions = true
                        }

                        if(parsedCaseDate != null && (parsedCaseDate.after(currentDate) || parsedCaseDate.compareTo(currentDate) == 0))
                        {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    navController?.navigate("editarcitaUsuario/${ultimaCita?.appointment_id}/${case.first.case_id}")
                                },
                                text = { Text("Confirmar o Cancelar Cita") }
                            )
                            hasOptions = true
                        }
                        if (!hasOptions) {
                            DropdownMenuItem(
                                onClick = { /* No hace nada ya que no hay opciones */ },
                                text = { Text("No hay opciones disponibles para este caso") }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Columna con la información de la cita (última cita, confirmación)
                Column {
                    Text(
                        text = "Última cita agendada:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    if (case.second.isNotEmpty()) {

                        val lastAppointment = case.second

                        // Fecha de la última cita
                        Text(
                            text = "Fecha: $lastAppointment",
                            fontSize = 14.sp,
                            color = if (case.first.suspended || (ultimaCita?.suspended == true)) Color.Red else Color.Blue
                        )

                            // Estado de confirmación
                        Text(
                            text = "Cita Confirmada: ${if (case.third) "Sí" else "No"}",
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Cita Suspendida: ${if (ultimaCita?.suspended == true) "Sí" else "No"}",
                            fontSize = 14.sp
                        )


                    } else {
                        Text(text = "Sin citas disponibles", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 17.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Estado: ${case.first.state}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(end = 8.dp)  // Espacio entre el texto y el círculo
                        )

                        // Círculo de color según el estado
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(
                                    when (case.first.state) {
                                        "Activo" -> Color.Yellow
                                        "Finalizado" -> Color.Green
                                        "Suspendido" -> Color.Red
                                        else -> Color.Gray
                                    }
                                )
                                .border(1.dp, Color.Black, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: String) {
    Text(
        text = notification,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFf0eee9))
            .padding(16.dp)
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
            .height(300.dp)
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
                .padding(8.dp),
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
        .replace(Regex("""\s*-\s*"""), "\n• ") // Convertir elementos de lista a viñetas con salto de línea
        .replace(Regex("""\*\*(.*?)\*\*"""), "\n$0\n") // Asegurar que los textos en negrita tengan saltos antes y después
        .replace(Regex("""\n{2,}"""), "\n") // Eliminar saltos de línea dobles o más
}

@Composable
fun CustomMarkdownText(content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        val lines = content.split("\n").map { it.trim() }
        for (line in lines) {
            if (line.isNotEmpty()) {
                Text(
                    text = parseMarkdownToAnnotatedString(line),
                    fontSize = if (line.startsWith("**") || line.startsWith("• **")) 18.sp else 16.sp, // Negritas y títulos más grandes
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
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

