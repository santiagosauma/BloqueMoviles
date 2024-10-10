package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.Case_CounterViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PantallaTemplateDetalleVistaCaso(
    navController: NavController?,
    caseId: String,
    route: String,
    barraNav: @Composable () -> Unit,
    contenidoExtra: @Composable () -> Unit,
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

                // Información del Cliente (común)
                SectionTitle("Información del Cliente")
                Spacer(modifier = Modifier.height(8.dp))

                //AUN FALTA MODIFICAR ESTO
                ClienteInfo("Nombre", "Edsel Cisneros James",Color.Black, FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Tipo (Cliente)", "Víctima",Color.Black, FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Lugar de Procedencia", "Guadalupe, Monterrey",Color.Black, FontWeight.Bold, FontWeight.Normal)

                Spacer(modifier = Modifier.height(16.dp))

                // Histórico (común)
                SectionTitle("Histórico de Citas")
                Spacer(modifier = Modifier.height(8.dp))



                val appointmentList = caseWithDetails?.second?.first

                appointmentList?.forEachIndexed { index, cita ->
                    val isLastItem = index == appointmentList.lastIndex

                    ClienteInfo(
                        campo = formatDate(cita.fecha.toDate()),
                        valor = formatTime(cita.fecha.toDate()),
                        campoColor = if (isLastItem) Color.Blue else Color.Black
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("agendar/$caseId")
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
                    valor = if (abogado.isNotEmpty()) abogado else "No hay un abogado asignado aun",
                    Color.Black,
                    FontWeight.Bold, FontWeight.Normal
                )
                ClienteInfo(
                    campo = "Estudiante Vinculado",
                    valor = if (estudiante.isNotEmpty()) estudiante else "No hay un estudiante asignado aun",
                    Color.Black,
                    FontWeight.Bold, FontWeight.Normal
                )


                Spacer(modifier = Modifier.height(8.dp))

                val usuariotipo by userViewModel.userType.collectAsState()

                LaunchedEffect(UserIdData.userId) {
                    UserIdData.userId?.let { userViewModel.fetchUserType(it) }
                }

                if(usuariotipo == "colaborador")
                {
                    Text(
                        text = "Colaboradores  y estudiantes disponibles",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    DropdownMenuColaboradores(colaboratorsandstudents)
                }


                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle("Comentarios")
                Spacer(modifier = Modifier.height(8.dp))



                val commentList = caseWithDetails?.second?.second

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    commentList?.forEach { comment ->
                        // Crear un estado independiente para almacenar el usuario de este comentario
                        val userName by comentViewModel.usuarioByComentario.collectAsState()

                        // Lanzar un efecto para obtener el usuario asociado a este comentario
                        LaunchedEffect(comment.comentario_id) {
                            comentViewModel.getUserNameByComentarioId(comment.comentario_id)
                        }

                        Text(
                            text = formatDate(comment.fecha.toDate()) + " " +
                                    formatTime(comment.fecha.toDate()),
                            color = Color.Blue)
                        ClienteComentario(
                            nombre = userName ?: "Usuario desconocido",
                            comentario = comment.contenido
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("comentar/$caseId")
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

                contenidoExtra()

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
                ) {
                    Text("Guardar", color = Color.White)
                }
            }
        }


        barraNav()
    }
}


@Composable
fun BotonesRepresentacion() {
    var selectedOption by remember { mutableStateOf("No Representar") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(top = 18.dp)
        ) {
            OutlinedButton(
                onClick = { selectedOption = "Representar" },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "Representar") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "Representar") Color.White else Color.Black
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
                Text("✔ Representar", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { selectedOption = "No Representar" },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedOption == "No Representar") Color(0xFF002366) else Color.White,
                    contentColor = if (selectedOption == "No Representar") Color.White else Color.Black
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
                Text("✖ No Representar", fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun BotonesEstado() {
    var selectedOption by remember { mutableStateOf("Activo") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(top = 18.dp)
        ) {
            OutlinedButton(
                onClick = { selectedOption = "Activo" },
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
                onClick = { selectedOption = "Suspendido" },
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
                onClick = { selectedOption = "Finalizado" },
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
fun ClienteInfo(campo: String,
                valor: String,
                campoColor: Color,
                fontWeightCampo: FontWeight = FontWeight.Normal,
                fontWeightValor: FontWeight = FontWeight.Normal
) {
    Row(modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = "$campo: ",
            fontWeight = fontWeightCampo,
            color = campoColor
        )
        Text(
            text = valor,
            fontWeight = fontWeightValor,
            color = campoColor
        )
    }
}


@Composable
fun ClienteComentario(nombre: String,
                      comentario: String
) {
    Column {
        Text(
            text = "$nombre: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(text = comentario, modifier = Modifier.padding(bottom = 8.dp))
    }
}

@Composable
fun DropdownMenuColaboradores(ColaboradoresEstudiantes: List<Usuario>?){
    var expanded by remember { mutableStateOf(false) }
    var selectedColaboradorOptionText by remember { mutableStateOf("Selecciona un colaborador") }
    var selectedEstudianteOptionText by remember { mutableStateOf("Selecciona un estudiante")}

    val colaboradores = mutableListOf<Usuario>()
    val estudiantes = mutableListOf<Usuario>()

    ColaboradoresEstudiantes?.forEach { usuario ->
        if (usuario.tipo == "colaborador") {
            colaboradores.add(usuario)
        } else {
            estudiantes.add(usuario)
        }
    }

    var expandedColaboradores by remember { mutableStateOf(false) }
    var expandedEstudiantes by remember { mutableStateOf(false) }

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
                    text = { Text(usuario.nombre + " " + usuario.apellidos) },
                    onClick = {
                        selectedColaboradorOptionText = usuario.nombre + " " + usuario.apellidos
                        expandedColaboradores = false
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

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
                    text = { Text(usuario.nombre + " " + usuario.apellidos) },
                    onClick = {
                        selectedEstudianteOptionText = usuario.nombre + " " + usuario.apellidos
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