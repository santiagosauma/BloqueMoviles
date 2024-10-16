package com.leotesta017.clinicapenal.view.templatesPantallas

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.Case_CounterViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun PantallaTemplateDetalleVistaCaso(
    navController: NavController?,
    caseId: String,
    route: String,
    routeAgendar: String,
    routeComentario: String,
    routeEditarFormulario: String,
    routeEditCita: String,
    routeEditComentario: String,
    barraNav: @Composable () -> Unit,
    contenidoExtra: @Composable (Case,Usuario,Usuario,String,String,String) -> Unit,
    caseViewModel: CaseViewModel = viewModel(),
    userViewModel: UsuarioViewModel = viewModel(),
    caseCounterViewModel: Case_CounterViewModel = viewModel(),
    comentViewModel: ComentarioViewModel = viewModel()
) {

    val caseWithDetails by caseViewModel.caseWithDetails.collectAsState()
    val error by caseViewModel.error.collectAsState()
    val colaboratorsandstudents by userViewModel.usersColaboratorsandStudents.collectAsState()
    var refactoredCaseId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(caseId) {
        caseViewModel.fetchCaseWithDetails(caseId)
    }

    LaunchedEffect(Unit) {
        userViewModel.fetchColaboratorsandStudents()
    }

    LaunchedEffect(caseId) {
        val index = caseCounterViewModel.findOrAddCase(caseId)
        refactoredCaseId = index
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            navController?.navigate(route = route)
                        }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Flecha",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Caso $refactoredCaseId",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                SectionTitle("Información del Cliente")

                Spacer(modifier = Modifier.height(8.dp))

                val extraInfoList = caseWithDetails?.second?.third
                var usuariocaso by remember {
                    mutableStateOf("")
                }

                extraInfoList?.lastOrNull()?.let { extraInfo ->

                    val userExtraInfoId = extraInfo.id_Usuario

                    LaunchedEffect(userExtraInfoId) {
                        userViewModel.fetchUsuario(userExtraInfoId)
                    }

                    // Recolectar la información del usuario
                    val usuarioExtraInfo by userViewModel.usuario.collectAsState()

                    usuariocaso = usuarioExtraInfo.id
                    ClienteInfoComponent(
                        userInfo = usuarioExtraInfo,
                        extraInfo = extraInfo,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController?.navigate("$routeEditarFormulario/$caseId")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
                ) {
                    Text("Editar Informacion del Caso", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle("Histórico de Citas")
                Spacer(modifier = Modifier.height(8.dp))



                val appointmentList = caseWithDetails?.second?.first

                appointmentList?.forEachIndexed { index, cita ->
                    val isLastItem = index == appointmentList.lastIndex

                    CitaInfo(
                        routeEditCita = routeEditCita,
                        caseId = caseId,
                        campo = formatDate(cita.fecha.toDate()),
                        valor = formatTime(cita.fecha.toDate()),
                        suspended = cita.suspended,
                        appointmentId = cita.appointment_id,
                        asistido = cita.asisted,
                        confirmado = cita.confirmed,
                        valoracion = cita.valoration,
                        navController = navController,
                        campoColor = if(cita.suspended) Color.Red
                                     else if (isLastItem) Color.Blue
                                     else Color.Black
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }


                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("$routeAgendar/$caseId")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
                ) {
                    Text("Agendar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Encargados (común)
                SectionTitle("Encargados")
                Spacer(modifier = Modifier.height(8.dp))


                val abogado by userViewModel.abogadoName.collectAsState()
                val estudiante by userViewModel.estudianteName.collectAsState()

                LaunchedEffect(caseWithDetails?.first?.lawyerAssigned) {
                    caseWithDetails?.first?.lawyerAssigned?.let {
                        userViewModel.fetchColaboratorOrStudentById(
                            it,"colaborador")
                    }
                }
                LaunchedEffect(caseWithDetails?.first?.studentAssigned) {
                    caseWithDetails?.first?.studentAssigned?.let{
                        userViewModel.fetchColaboratorOrStudentById(it, "estudiante")
                    }
                }



                ClienteInfo(
                    campo = "Agente a cargo",
                    valor = abogado.ifEmpty { "No hay un abogado asignado aun" },
                    Color.Black,

                )
                ClienteInfo(
                    campo = "Estudiante Vinculado",
                    valor = estudiante.ifEmpty { "No hay un estudiante asignado aun" },
                    Color.Black,
                )


                Spacer(modifier = Modifier.height(8.dp))

                val usuariotipo by userViewModel.userType.collectAsState()

                LaunchedEffect(UserIdData.userId) {
                    UserIdData.userId?.let { userViewModel.fetchUserType(it) }
                }

                var colaboradorSeleccionado by remember { mutableStateOf<Usuario?>(null) }
                var estudianteSeleccionado by remember { mutableStateOf<Usuario?>(null) }

                if(usuariotipo == "colaborador")
                {
                    Text(
                        text = "Colaboradores  y estudiantes disponibles",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )

                    DropdownMenuColaboradores(
                        colaboradoresEstudiantes = colaboratorsandstudents,  // Lista que contiene tanto colaboradores como estudiantes
                        onColaboradorSelected = { colaborador ->
                            colaboradorSeleccionado = colaborador  // Guardar colaborador seleccionado
                        },
                        onEstudianteSelected = { student ->
                            estudianteSeleccionado = student  // Guardar estudiante seleccionado
                        }
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle("Comentarios")
                Spacer(modifier = Modifier.height(8.dp))



                val commentList = caseWithDetails?.second?.second

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    commentList?.forEach { comment ->
                        val userName = comentViewModel.usuarioByComentario.collectAsState().value[comment.comentario_id]

                        // Lanzar un efecto para obtener el nombre de usuario si no está en el mapa
                        LaunchedEffect(comment.comentario_id) {
                            if (userName == null) {
                                comentViewModel.getUserNameByComentarioId(comment.comentario_id)
                            }
                        }

                        Text(
                            text = formatDate(comment.fecha.toDate()) + " " + formatTime(comment.fecha.toDate()),
                            color = Color.Blue
                        )


                        ClienteComentario(
                            nombre = userName ?: "Cargando...",  // Muestra "Cargando..." mientras se espera el nombre
                            comentario = comment.contenido,
                            isImportant = comment.important,
                            author = comment.madeBy,
                            comentarioId = comment.comentario_id,
                            navController = navController,
                            caseId = caseId,
                            routeEditComentario = routeEditComentario
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val currentUserName = if(caseWithDetails?.first?.lawyerAssigned == UserIdData.userId) {
                    abogado
                } else {
                    estudiante
                }
                val destinatario = if(caseWithDetails?.first?.lawyerAssigned == UserIdData.userId) {
                    caseWithDetails?.first?.studentAssigned
                } else {
                    caseWithDetails?.first?.lawyerAssigned
                }

                Button(
                    onClick = {
                        navController?.navigate("$routeComentario/$caseId/$destinatario/$currentUserName")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
                ) {
                    Text("Comentar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                caseWithDetails?.first?.let { datos ->
                    contenidoExtra(
                        datos,
                        colaboradorSeleccionado ?: Usuario(id = "", nombre = "Sin abogado", apellidos = ""),
                        estudianteSeleccionado ?: Usuario(id = "", nombre = "Sin estudiante", apellidos = ""),
                        abogado,
                        estudiante,
                        usuariocaso
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        barraNav()
    }
}


@Composable
fun ClienteInfoComponent(
    userInfo: Usuario,
    extraInfo: ExtraInfo,
) {
    // Mostrar información básica del cliente
    ClienteInfo(
        campo = "Nombre Completo",
        valor = "${userInfo.nombre} ${userInfo.apellidos}",
        Color.Black,
    )

    ClienteInfo(
        campo = "Tipo (Cliente)",
        valor = if (extraInfo.victima) "Víctima" else if (extraInfo.investigado) "Investigado" else "No se encontró información",
        Color.Black,
    )

    ClienteInfo(
        campo = "Lugar de Procedencia",
        valor = extraInfo.lugarProcedencia,
        Color.Black,
    )

    // Información del Segundo Formulario
    ClienteInfo(
        campo = "Fiscalía",
        valor = extraInfo.fiscalia.ifEmpty { "No se ha respondido aún el segundo formulario" },
        Color.Black,
    )

    ClienteInfo(
        campo = "Crimen",
        valor = extraInfo.crime.ifEmpty { "No se ha respondido aún el segundo formulario" },
        Color.Black,
    )

    ClienteInfo(
        campo = "INE",
        valor = extraInfo.ine.ifEmpty { "No se ha respondido aún el segundo formulario" },
        Color.Black,
    )

    Spacer(modifier = Modifier.height(16.dp))
    SectionTitle(title = "Información del Caso")
    Spacer(modifier = Modifier.height(16.dp))

        // Mostrar datos específicos del caso
        ClienteInfo(
        campo = "Número Único de Causa",
        valor = extraInfo.nuc.ifEmpty { "No hay información aún" },
        Color.Black,
        )

        ClienteInfo(
            campo = "Carpeta Judicial",
            valor = extraInfo.carpetaJudicial.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Carpeta de Investigación",
            valor = extraInfo.carpetaInvestigacion.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Acceso a la Fiscalía Virtual",
            valor = extraInfo.afv.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Contraseña a la Fiscalía Virtual",
            valor = extraInfo.passwordFV.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Fiscal Titular",
            valor = extraInfo.fiscalTitular.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Unidad de Investigación",
            valor = extraInfo.unidadInvestigacion.ifEmpty { "No hay información aún" },
            Color.Black,
        )

        ClienteInfo(
            campo = "Dirección de la Unidad de Investigación",
            valor = extraInfo.direccionUI.ifEmpty { "No hay información aún" },
            Color.Black,
        )

}

@Composable
fun BotonesRepresentacion(
    caso: Case,
    onRepresented: (Boolean) -> Unit
) {
    // Estado inicial basado en si el caso está representado
    var selectedOption by remember { mutableStateOf(if (caso.represented) "Representado" else "No Representar") }
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(top = 18.dp)
        ) {
            // Botón de Representar
            OutlinedButton(
                onClick = {
                    if (!caso.represented) {
                        // Si el caso no está representado, cambia el estado a "Representado"
                        selectedOption = "Representado"
                        onRepresented(true)  // Notificar que ahora el caso está representado
                    }
                    else{
                        selectedOption = "Representado"
                        onRepresented(true)
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Representado") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "Representado") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 24.dp, bottomStart = 24.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (selectedOption == "Representado") "Representando" else "✔ Representar",
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón de Dejar de Representar o No Representar
            OutlinedButton(
                onClick = {
                    if (caso.represented) {
                        // Si el caso está representado, cambiar a "Dejar de representar"
                        selectedOption = "Dejar de representar"
                        onRepresented(false)  // Notificar que ahora el caso ya no está representado
                    } else {
                        // Si no está representado, cambiar a "No Representar"
                        selectedOption = "No Representar"
                        onRepresented(false)  // Notificar que sigue sin estar representado
                    }
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "No Representar" || selectedOption == "Dejar de representar") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "No Representar" || selectedOption == "Dejar de representar") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 24.dp, bottomEnd = 24.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = if (selectedOption == "Representado") "Dejar de representar" else "✖ No Representar",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



@Composable
fun BotonesEstado(
    caso: Case,
    onEstadoChange: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(caso.state) }

    LaunchedEffect(Unit) {
        onEstadoChange(selectedOption)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(top = 18.dp)
        ) {
            OutlinedButton(
                onClick = {
                    selectedOption = "Activo"
                    onEstadoChange("Activo") // Notificar el cambio de estado
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Activo") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "Activo") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 24.dp, bottomStart = 24.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Activo", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = {
                    selectedOption = "Suspendido"
                    onEstadoChange("Suspendido") // Notificar el cambio de estado
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Suspendido") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "Suspendido") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 0.dp, bottomEnd = 0.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Suspendido", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = {
                    selectedOption = "Finalizado"
                    onEstadoChange("Finalizado") // Notificar el cambio de estado
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Finalizado") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "Finalizado") Color.White else Color.Black
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp, bottomStart = 0.dp,
                    topEnd = 24.dp, bottomEnd = 24.dp
                ),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text("Finalizado", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CitaInfo(
    routeEditCita:String,
    caseId: String,
    campo: String,
    valor: String,
    campoColor: Color,
    suspended: Boolean,
    appointmentId: String,
    asistido: Boolean,
    confirmado: Boolean,
    valoracion: Int,
    navController: NavController?,
    fontWeightCampo: FontWeight = FontWeight.Normal,
    fontWeightValor: FontWeight = FontWeight.Normal
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        val currentDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val appointmentDate: Date? = try {
            formatter.parse("$campo $valor")
        } catch (e: Exception) {
            null
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            // Mostrar el campo y valor
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "$campo:  $valor",
                    fontWeight = fontWeightCampo,
                    color = campoColor,
                    modifier = Modifier.fillMaxWidth()
                )

                appointmentDate?.let { date ->
                    val oneHourAfter = Date(date.time + 60 * 60 * 1000)

                    when {
                        suspended -> {
                            Text(
                                text = "Cita suspendida",
                                fontWeight = fontWeightCampo,
                                color = campoColor,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        date.before(currentDate) && currentDate.before(oneHourAfter) -> {
                            Text(
                                text = "Cita en progreso",
                                fontWeight = fontWeightCampo,
                                color = Color.Blue,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        date.before(currentDate) && currentDate.after(oneHourAfter) -> {
                            Text(
                                text = "Cita completada",
                                fontWeight = fontWeightCampo,
                                color = campoColor,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        !confirmado -> {
                            Text(
                                text = "Cita no confirmada",
                                fontWeight = fontWeightCampo,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        confirmado -> {
                            Text(
                                text = "Cita confirmada",
                                fontWeight = fontWeightCampo,
                                color = Color.Blue,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        !asistido ->{
                            Text(
                                text = "No se presentó a la cita",
                                fontWeight = fontWeightCampo,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        asistido ->{
                            Text(
                                text = "Asistio a la cita",
                                fontWeight = fontWeightCampo,
                                color = Color.Blue,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                } ?: run {
                    Text(
                        text = "Fecha inválida",
                        fontWeight = fontWeightCampo,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Icono de edición

                IconButton(
                    onClick = {
                        navController?.navigate("$routeEditCita/$caseId/$appointmentId")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar cita",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }

        }

        // Mostrar barra de estrellas debajo de la cita completada
        if (!suspended && appointmentDate != null && appointmentDate.before(currentDate)) {
            Spacer(modifier = Modifier.height(4.dp))
            RatingBar(rating = valoracion)
        }
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),  // Asegura que la barra de valoración ocupe todo el ancho
        horizontalArrangement = Arrangement.Start
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = if (i <= rating) "Estrella llena" else "Estrella vacía",
                tint = Color(0xFFFFD700), // Color dorado
                modifier = Modifier.size(24.dp)
            )
        }
    }
}



@Composable
fun ClienteInfo(campo: String,
                valor: String,
                campoColor: Color,
) {
    Row(modifier = Modifier.padding(start = 16.dp)) {
        BasicText(
            text = AnnotatedString.Builder().apply {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = campoColor)) {
                    append("$campo: ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = campoColor)) {
                    append(valor)
                }
            }.toAnnotatedString(),
            style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),

        )
    }
}


@Composable
fun ClienteComentario(nombre: String,
                      comentario: String,
                      isImportant: Boolean,
                      author: String,
                      comentarioId:String,
                      caseId:String,
                      navController:NavController?,
                      routeEditComentario: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column {
            Text(
                text = "$nombre: ",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = if(isImportant) "$comentario \nMensaje Importante"
            else comentario,
                modifier = Modifier.padding(bottom = 8.dp),
                color = if(isImportant) Color.Red else Color.Black
            )
        }
        val userId = UserIdData.userId
        if (userId == author)
        {
            IconButton(
                onClick = {
                    navController?.navigate("$routeEditComentario/$caseId/$comentarioId")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar Comentario",
                    tint = Color.Black,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
fun DropdownMenuColaboradores(
    colaboradoresEstudiantes: List<Usuario>?,
    onColaboradorSelected: (Usuario) -> Unit,
    onEstudianteSelected: (Usuario) -> Unit
) {
    var selectedColaboradorOptionText by remember { mutableStateOf("Selecciona un colaborador") }
    var selectedEstudianteOptionText by remember { mutableStateOf("Selecciona un estudiante") }

    val colaboradores = colaboradoresEstudiantes?.filter { it.tipo == "colaborador" } ?: emptyList()
    val estudiantes = colaboradoresEstudiantes?.filter { it.tipo == "estudiante" } ?: emptyList()

    var expandedColaboradores by remember { mutableStateOf(false) }
    var expandedEstudiantes by remember { mutableStateOf(false) }

    // Dropdown para seleccionar colaborador
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedButton(
            onClick = { expandedColaboradores = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedColaboradorOptionText)
        }
        DropdownMenu(
            expanded = expandedColaboradores,
            onDismissRequest = { expandedColaboradores = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            colaboradores.forEach { usuario ->
                DropdownMenuItem(
                    text = { Text("${usuario.nombre} ${usuario.apellidos}") },
                    onClick = {
                        selectedColaboradorOptionText = "${usuario.nombre} ${usuario.apellidos}"
                        onColaboradorSelected(usuario)
                        expandedColaboradores = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Dropdown para seleccionar estudiante
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedButton(
            onClick = { expandedEstudiantes = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedEstudianteOptionText)
        }
        DropdownMenu(
            expanded = expandedEstudiantes,
            onDismissRequest = { expandedEstudiantes = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            estudiantes.forEach { usuario ->
                DropdownMenuItem(
                    text = { Text("${usuario.nombre} ${usuario.apellidos}") },
                    onClick = {
                        selectedEstudianteOptionText = "${usuario.nombre} ${usuario.apellidos}"
                        onEstudianteSelected(usuario)
                        expandedEstudiantes = false
                    }
                )
            }
        }
    }
}


fun formatDate(date: Date?): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return date?.let { dateFormat.format(it) } ?: ""
}

fun formatTime(date: Date?): String {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return date?.let { timeFormat.format(it) } ?: ""
}