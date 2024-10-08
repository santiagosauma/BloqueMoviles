package com.leotesta017.clinicapenal.view.templatesPantallas

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SectionTitle
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar

@Composable
fun PantallaTemplateDetalleVistaCaso(
    navController: NavController?,
    caseId: String,
    route: String,
    barraNav: @Composable () -> Unit,
    contenidoExtra: @Composable () -> Unit
) {
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
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Flecha",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Caso $caseId",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información del Cliente (común)
                SectionTitle("Información del Cliente")
                Spacer(modifier = Modifier.height(8.dp))
                ClienteInfo("Nombre", "Edsel Cisneros James", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Tipo (Cliente)", "Víctima", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Lugar de Procedencia", "Guadalupe, Monterrey", FontWeight.Bold, FontWeight.Normal)

                Spacer(modifier = Modifier.height(16.dp))

                // Histórico (común)
                SectionTitle("Histórico")
                Spacer(modifier = Modifier.height(8.dp))
                ClienteInfo("19/06/2024", "Cita Realizada (6:30)")
                ClienteInfo("12/08/2024", "Próxima Cita (5:30)")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("agendar")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Agendar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Encargados (común)
                SectionTitle("Encargados")
                Spacer(modifier = Modifier.height(8.dp))
                ClienteInfo("Agente a cargo", "Raul Jiménez", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Estudiante Vinculado", "Luis Domínguez", FontWeight.Bold, FontWeight.Normal)

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown menu (común)
                Text(
                    text = "Colaboradores disponibles",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                DropdownMenuExample()

                Spacer(modifier = Modifier.height(16.dp))

                // Comentarios (común)
                SectionTitle("Comentarios")
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ClienteComentario("Raul Jiménez", "En espera de documentos para continuar proceso")
                    ClienteComentario("Luis Domínguez", "Documentos entregados día Lunes 19 de Agosto")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("comentar")
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Comentar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Aquí se inyecta el contenido extra (diferente para cada pantalla)
                contenidoExtra()

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Guardar (común)
                Button(
                    onClick = { /* Acción Guardar */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Guardar", color = Color.White)
                }
            }
        }

        // Barra de navegación (diferente para cada pantalla)
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
                    containerColor = if (selectedOption == "Representar") Color.Blue else Color.White,
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
                    containerColor = if (selectedOption == "No Representar") Color.Blue else Color.White,
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
                    containerColor = if (selectedOption == "Activo") Color.Blue else Color.White,
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
                    containerColor = if (selectedOption == "Suspendido") Color.Blue else Color.White,
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
                    containerColor = if (selectedOption == "Finalizado") Color.Blue else Color.White,
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
fun ClienteInfo(campo: String, valor: String, fontWeightCampo: FontWeight = FontWeight.Normal, fontWeightValor: FontWeight = FontWeight.Normal) {
    Row(modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = "$campo: ",
            fontWeight = fontWeightCampo
        )
        Text(
            text = valor,
            fontWeight = fontWeightValor
        )
    }
}


@Composable
fun ClienteComentario(nombre: String, comentario: String) {
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
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Selecciona un colaborador") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedOptionText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            DropdownMenuItem(
                text = { Text("Colaborador 1") },
                onClick = {
                    selectedOptionText = "Colaborador 1"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Colaborador 2") },
                onClick = {
                    selectedOptionText = "Colaborador 2"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Colaborador 3") },
                onClick = {
                    selectedOptionText = "Colaborador 3"
                    expanded = false
                }
            )
        }
    }
}