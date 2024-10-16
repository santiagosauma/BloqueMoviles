package com.leotesta017.clinicapenal.view.templatesPantallas

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.notificaciones.NotificationService
import com.leotesta017.clinicapenal.notificaciones.NotificationServiceSingleton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PantallaComentarTemplate(
    navController: NavController?,
    comentarioIncial: String? = null, // Comentario inicial puede ser nulo
    title: String,
    caseId: String,
    route: String,
    comentarioId: String? = null, // ID del comentario para eliminación, puede ser nulo
    bottomBarNav: @Composable () -> Unit,
    isUrgentInitial: Boolean? = null, // Urgente puede ser nulo
    destinatario: String? = null,
    currentUsername: String? = null,
    isEditing: Boolean = false, // Para habilitar el botón de eliminar solo si está editando
    onAddOrEditComment: (String, Boolean, String, String) -> Unit,
    onDeleteComment: (String) -> Unit // Callback para manejar la acción de eliminar el comentario, usando el ID
) {
    var comentario by remember { mutableStateOf(comentarioIncial ?: "") } // Valor predeterminado vacío si es nulo
    var isBold by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showUnsavedChangesDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) } // Controla la visibilidad del diálogo de eliminación
    var linkInput by remember { mutableStateOf("") }
    var isUrgent by remember { mutableStateOf(isUrgentInitial ?: false) } // Valor predeterminado 'false' si es nulo
    var hasChanges by remember { mutableStateOf(false) } // Variable booleana para detectar cambios

    Scaffold(
        topBar = {
            Column {
                TopBar()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 3.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        // Si hay cambios sin guardar, mostrar diálogo
                        if (hasChanges) {
                            showUnsavedChangesDialog = true
                        } else {
                            navController?.navigate("$route/$caseId")
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            bottomBarNav()
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "Insertar enlace",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color(0xFFF2F2F2))
                        .padding(8.dp)
                ) {
                    BasicTextField(
                        value = comentario,
                        onValueChange = {
                            comentario = it
                            hasChanges = true // Marcar que ha habido cambios
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
                        ),
                        decorationBox = { innerTextField ->
                            if (comentario.isEmpty()) {
                                Text(
                                    text = "Inserte Comentario...",
                                    style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                ){
                    OutlinedButton(
                        onClick = { isUrgent = !isUrgent
                                  if(isUrgentInitial != null && isUrgentInitial != isUrgent)
                                  {
                                      hasChanges = true
                                  }
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isUrgent) Color.Red else Color(0xFF002366),
                            contentColor = if (isUrgent) Color.White else Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (isUrgent) "Urgente" else "Marcar como urgente")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val context = LocalContext.current
                    val userId = UserIdData.userId

                    val notificationService = NotificationServiceSingleton.getInstance(context)
                    RoundedButton(
                        icon = Icons.Default.Save,
                        label = "Guardar",
                        onClick = {
                            if (comentario.isNotEmpty()) {
                                if(isEditing)
                                {
                                    if (userId != null && comentarioId != null) {
                                        onAddOrEditComment(comentario, isUrgent, userId, comentarioId)
                                    }
                                }
                                else{
                                    if (userId != null) {
                                        onAddOrEditComment(comentario, isUrgent, userId, caseId)

                                        if(destinatario != null && currentUsername != null) {
                                            MensajeNuevoNotificaciones(
                                                comentario = comentario,
                                                idUsertoSend = destinatario,
                                                isUrgent = isUrgent,
                                                notificationService = notificationService,
                                                UserWhoSendend = currentUsername
                                            )
                                        }

                                    }
                                    comentario = comentarioIncial ?: ""  // Limpiar el campo después de guardar
                                    isUrgent = isUrgentInitial ?: false  // Reiniciar el estado de urgencia
                                }

                            }
                            hasChanges = false // Reiniciar el estado de cambios después de guardar
                        }
                    )

                    RoundedButton(
                        icon = Icons.Default.Delete,
                        label = "Descartar",
                        onClick = {
                            comentario = comentarioIncial ?: "" // Restaurar el comentario inicial o vacío
                            isUrgent = isUrgentInitial ?: false // Restaurar el estado de urgencia inicial
                            hasChanges = false // Reiniciar el estado de cambios
                        }
                    )
                }

                // Mostrar el botón de eliminar si está en modo edición y tiene un comentario ID
                if (isEditing && comentarioId != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            showDeleteDialog = true // Mostrar el diálogo de confirmación de eliminación
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Eliminar Comentario")
                    }
                }

                // Mostrar el diálogo para insertar enlace
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Insertar enlace") },
                        text = {
                            Column {
                                Text("Introduce el enlace:")
                                TextField(
                                    value = linkInput,
                                    onValueChange = { linkInput = it },
                                    placeholder = { Text("https://") }
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    comentario += "\nHipervinculo: $linkInput" // Insertar el enlace en el comentario
                                    hasChanges = true
                                    showDialog = false
                                }
                            ) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                // Mostrar el diálogo de advertencia al salir sin guardar
                if (showUnsavedChangesDialog) {
                    AlertDialog(
                        onDismissRequest = { showUnsavedChangesDialog = false },
                        title = { Text("Cambios sin guardar") },
                        text = { Text("Tienes cambios sin guardar, ¿deseas salir de todos modos?") },
                        confirmButton = {
                            Button(onClick = {
                                showUnsavedChangesDialog = false
                                navController?.navigate("$route/$caseId") // Salir sin guardar
                            }) {
                                Text("Salir")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                showUnsavedChangesDialog = false // Cerrar el diálogo y no salir
                            }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                // Mostrar el diálogo de confirmación de eliminación
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Estás seguro de que deseas eliminar este comentario? Esta acción no se puede deshacer.") },
                        confirmButton = {
                            Button(onClick = {
                                showDeleteDialog = false
                                comentarioId?.let {
                                    onDeleteComment(it) // Ejecutar la lógica de eliminación con el comentarioId
                                }
                            }) {
                                Text("Eliminar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = {
                                    showDeleteDialog = false // Cerrar el diálogo sin eliminar
                                }
                            ) {
                                Text("Cancelar")
                            }
                        }
                    )
                }
            }
        }
    )
}


fun MensajeNuevoNotificaciones(
    comentario: String,
    idUsertoSend: String,
    UserWhoSendend: String,
    notificationService: NotificationService,
    isUrgent: Boolean

)
{
    CoroutineScope(Dispatchers.IO).launch {
        notificationService.sendMessageNotificationToAssignedSpecificUser(
            title = if(isUrgent) "Mensaje urgente nuevo" else "Mensaje Nuevo",
            message = "$UserWhoSendend \n$comentario",
            user_id = idUsertoSend
        )
    }
}

