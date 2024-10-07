package com.leotesta017.clinicapenal.view.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewComentarios(navController: NavController?, case_id: String) {
    var comentario by remember { mutableStateOf(TextFieldValue("Inserte comentario...")) }
    var rating by remember { mutableStateOf(0) }
    var hasClicked by remember { mutableStateOf(false) }

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
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Valorar Caso",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            BarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))

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
                            tint = if(index < rating) Color(0xFF0B1F8C) else Color.Black,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    rating = index + 1
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Comentario (Opcional)",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = comentario,
                    onValueChange = {
                        comentario = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                        .padding(16.dp)
                        .clickable {
                            if (!hasClicked) {
                                comentario = TextFieldValue("")
                                hasClicked = true
                            }
                        },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = if (comentario.text.isEmpty() && !hasClicked) Color.Gray else Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    keyboardActions = KeyboardActions.Default
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Acción para enviar la valoración
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Enviar Valoración")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ReviewComentariosPreview() {
    ClinicaPenalTheme {
        ReviewComentarios(navController = rememberNavController(), case_id = "1")
    }
}
