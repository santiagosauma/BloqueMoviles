package com.leotesta017.clinicapenal.usuarioColaborador

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar


@Composable
fun ActualizarCasos(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Caso ID124",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )

                // Sección de Representación (con dos botones)
                SectionTitle("Representación")
                Spacer(modifier = Modifier.height(8.dp))

                // Botones en una sola fila
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // Asegurar que estén centrados
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Acción Representar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("✔ Representar", color = Color.Black) // Cambia el color del texto a negro
                    }
                    Button(
                        onClick = { /* Acción No Representar */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("✖ No Representar", color = Color.Black) // Cambia el color del texto a negro
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Información del cliente
                SectionTitle("Información del Cliente")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "• Nombre: Edsel Cisneros James", modifier = Modifier.padding(start = 16.dp))
                Text(text = "• Tipo (Cliente): Víctima", modifier = Modifier.padding(start = 16.dp))
                Text(text = "• Lugar de Procedencia: Guadalupe, Monterrey", modifier = Modifier.padding(start = 16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                // Sección de Histórico
                SectionTitle("Histórico")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "19/06/2024 - Cita Realizada (6:30)", modifier = Modifier.padding(start = 16.dp))
                Text(text = "12/08/2024 - Próxima Cita (5:30)", modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { /* Acción Agendar */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp) // Botón más bajo
                ) {
                    Text("Agendar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Encargados
                SectionTitle("Comentarios")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "• Agente a cargo: Raul Jiménez", modifier = Modifier.padding(start = 16.dp))
                Text(text = "• Estudiante Vinculado: Luis Domínguez", modifier = Modifier.padding(start = 16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // Reasignar colaborador
                Text(text = "Colaboradores disponibles", modifier = Modifier.padding(start = 16.dp))
                DropdownMenuExample()

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Acción Reasignar */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp) // Botón más bajo
                ) {
                    Text("Re-Asginar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Comentarios
                SectionTitle("Comentarios")
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(text = "Raul Jiménez: En espera de documentos para continuar proceso")
                    Text(text = "Luis Domínguez: Documentos entregados día Lunes 19 de Agosto")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Acción Comentar */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp) // Botón más bajo
                ) {
                    Text("Comentar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Estado de Representación
                SectionTitle("Representación")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Acción Activo */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp) // Botón más bajo
                            .padding(end = 8.dp)
                    ) {
                        Text("Activo")
                    }
                    Button(
                        onClick = { /* Acción Suspendido */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp) // Botón más bajo
                            .padding(end = 8.dp)
                    ) {
                        Text("Suspendido")
                    }
                    Button(
                        onClick = { /* Acción Finalizado */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp) // Botón más bajo
                    ) {
                        Text("Finalizado")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Acción Guardar */ },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp) // Botón más bajo
                ) {
                    Text("Guardar")
                }
            }
        }

        // Barra de navegación en la parte inferior
        AdminBarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}


@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("Selecciona un colaborador") }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Button(onClick = { expanded = !expanded }) {
            Text(selectedOptionText)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
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

@Preview (showBackground = true)
@Composable
fun PreviewActualizarCasos() {
    ActualizarCasos(navController = null)
}