package com.leotesta017.clinicapenal.view.usuarioGeneral

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel

@Composable
fun ReviewComentarios(
    navController: NavController?,
    case_id: String,
    caseViewModel: CaseViewModel = viewModel()
) {
    var comentario by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(0) }
    var hasClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = { ReviewTopBar(navController) },
        bottomBar = { BarraNav(navController = navController, modifier = Modifier.fillMaxWidth()) },
        content = { paddingValues ->
            ReviewContent(
                paddingValues = paddingValues,
                comentario = comentario,
                onComentarioChange = { comentario = it },
                rating = rating,
                onRatingChange = { rating = it },
                hasClicked = hasClicked,
                onClickComentario = { hasClicked = true },
                onEnviarClick = {
                    if (rating > 0) {
                        UserIdData.userId?.let {
                            handleEnviarClick(
                                rating = rating,
                                comentario = comentario.text,
                                caseId = case_id,
                                userId = it,
                                caseViewModel = caseViewModel,
                                context = context,
                                navController = navController
                            )
                        }
                    }
                    else{
                        Toast.makeText(context, "Debe seleccionar una valoración", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    )
}


@Composable
fun ReviewTopBar(navController: NavController?) {
    Column {
        TopBar()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 3.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Valorar Cita",
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ReviewContent(
    paddingValues: PaddingValues,
    comentario: TextFieldValue,
    onComentarioChange: (TextFieldValue) -> Unit,
    rating: Int,
    onRatingChange: (Int) -> Unit, // Callback para el cambio de valoración
    hasClicked: Boolean,
    onClickComentario: () -> Unit,
    onEnviarClick:  () -> Unit // Callback para enviar la información
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        ReviewRating(rating = rating, onRatingChange = onRatingChange)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Comentario (Opcional)",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ReviewComentarioInput(
            comentario = comentario,
            onComentarioChange = onComentarioChange,
            hasClicked = hasClicked,
            onClickComentario = onClickComentario
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onEnviarClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366), contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Valoración")
        }
    }
}

@Composable
fun ReviewRating(rating: Int, onRatingChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Default.Star else Icons.Default.StarOutline,
                contentDescription = "Estrella ${index + 1}",
                tint = if (index < rating) Color(0xFF002366) else Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onRatingChange(index + 1) // Llamar al callback con la nueva valoración
                    }
            )
        }
    }
}

@Composable
fun ReviewComentarioInput(
    comentario: TextFieldValue,
    onComentarioChange: (TextFieldValue) -> Unit,
    hasClicked: Boolean,
    onClickComentario: () -> Unit
) {
    BasicTextField(
        value = comentario,
        onValueChange = onComentarioChange, // Callback para cambiar el comentario
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.small)
            .padding(16.dp)
            .clickable {
                if (!hasClicked) {
                    onComentarioChange(TextFieldValue("")) // Callback para resetear el comentario
                    onClickComentario() // Marcar que el campo ha sido clicado
                }
            },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = if (comentario.text.isEmpty() && !hasClicked) Color.Gray else Color.Black,
            textAlign = TextAlign.Start
        ),
        keyboardActions = KeyboardActions.Default
    )
}

fun handleEnviarClick(
    caseViewModel: CaseViewModel,
    caseId: String,
    rating: Int,
    comentario: String,
    userId: String,
    context: Context,
    navController: NavController?
) {

    if (rating > 0) {
        caseViewModel.updateAppointmentAndAddComment(
            caseId = caseId,
            rating = rating,
            commentContent = comentario.trim(), // Evitar comentarios vacíos con espacios
            userId = userId
        )
        Toast.makeText(context, "Valoración enviada exitosamente", Toast.LENGTH_LONG).show()
        navController?.navigate("solicitud")
    } else {
        Log.d("Error", "Debe seleccionar una valoración")
        Toast.makeText(context, "Debe seleccionar una valoración", Toast.LENGTH_SHORT).show()
    }
}


