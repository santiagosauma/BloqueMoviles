package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.usuarioColaborador.ClienteComentario
import com.leotesta017.clinicapenal.view.usuarioColaborador.ClienteInfo
import com.leotesta017.clinicapenal.view.usuarioColaborador.DropdownMenuExample
import com.leotesta017.clinicapenal.view.usuarioGeneral.SectionTitle

@Composable
fun DetalleCasoEstudiante(navController: NavController?) {
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
                            navController?.navigate("generarsolicitudestudiante")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Flecha",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Caso ID124",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle("Información del Cliente")
                Spacer(modifier = Modifier.height(8.dp))
                ClienteInfo("Nombre", "Edsel Cisneros James", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Tipo (Cliente)", "Víctima", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Lugar de Procedencia", "Guadalupe, Monterrey", FontWeight.Bold, FontWeight.Normal)

                Spacer(modifier = Modifier.height(16.dp))

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

                SectionTitle("Encargados")
                Spacer(modifier = Modifier.height(8.dp))
                ClienteInfo("Agente a cargo", "Raul Jiménez", FontWeight.Bold, FontWeight.Normal)
                ClienteInfo("Estudiante Vinculado", "Luis Domínguez", FontWeight.Bold, FontWeight.Normal)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Colaboradores disponibles",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
                DropdownMenuExample()

                Spacer(modifier = Modifier.height(16.dp))

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

        EstudiantesBarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetalleCasoEstudiante() {
    DetalleCasoEstudiante(navController = null)
}
