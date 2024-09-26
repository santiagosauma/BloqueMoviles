package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.ApplyStyleButtons
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TextEditor
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@Composable
fun PantallaAgregarInformacionTemplate(
    navController: NavController?,
    titulo: String,
    textDescripcion: String,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues, String, (String) -> Unit) -> Unit
) {
    var currentTextStyle by remember { mutableStateOf(TextStyle.Default) }
    var textContent by remember { mutableStateOf(textDescripcion) }

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            Column {
                bottomBar()
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    content(paddingValues, textContent) { newText ->
                        textContent = newText
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Colocamos los botones de estilo arriba del editor
                    ApplyStyleButtons(
                        onApplyBold = {
                            currentTextStyle = currentTextStyle.copy(
                                fontWeight = if (currentTextStyle.fontWeight == FontWeight.Bold) FontWeight.Normal else FontWeight.Bold
                            )
                        },
                        onApplyItalic = {
                            currentTextStyle = currentTextStyle.copy(
                                fontStyle = if (currentTextStyle.fontStyle == FontStyle.Italic) FontStyle.Normal else FontStyle.Italic
                            )
                        },
                        onApplyUnderline = {
                            // Aquí puedes manejar el subrayado de manera personalizada si es necesario
                        }
                    )

                    // Editor de texto más grande y scrollable
                    TextEditor(
                        initialText = textContent,
                        onTextChange = { newText -> textContent = newText },
                        applyStyle = { currentTextStyle }
                    )
                }
            }
        }
    )
}



@Composable
fun AgregarInfoTemplate(
    navController: NavController?,
    titulo: String,
    textDescripcion: String,
    bottomBarContent: @Composable () -> Unit,
    onAddClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    PantallaAgregarInformacionTemplate(
        navController = navController,
        titulo = titulo,
        textDescripcion = textDescripcion,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(
                    icon = Icons.Default.Edit,
                    label = "Añadir",
                    onClick = onAddClick
                )
                RoundedButton(
                    icon = Icons.Default.Delete,
                    label = "Cancelar",
                    onClick = onCancelClick
                )
            }
            bottomBarContent()
        },
        content = { paddingValues, textContent, onTextChange ->
            var nombre by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )


            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .height(100.dp)
                    .verticalScroll(rememberScrollState())
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Contenido",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 8.dp, start = 10.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
        }
    )
}

