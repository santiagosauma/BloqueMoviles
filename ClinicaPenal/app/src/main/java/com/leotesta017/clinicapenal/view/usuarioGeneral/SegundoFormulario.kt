package com.leotesta017.clinicapenal.view.usuarioGeneral

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel

@Composable
fun SegundoFormulario(navController: NavController?,caseId: String) {
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
                    IconButton(onClick = { navController?.navigate("solicitud") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Formulario Representación",
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var delitoIngresado by remember { mutableStateOf("") }
                var fiscaliaSeleccionada by remember { mutableStateOf("") }
                var ineIngresado by remember { mutableStateOf("") }

                // Input para el delito
                InputDelito(onDelitoChange = { nuevoDelito ->
                    delitoIngresado = nuevoDelito
                })

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown para la fiscalía
                InputFiscalia(onFiscaliaEntered = { nuevaFiscalia ->
                    fiscaliaSeleccionada = nuevaFiscalia
                })

                Spacer(modifier = Modifier.height(16.dp))

                // Input para el INE
                InputCI(onIneChange = { nuevoINE ->
                    ineIngresado = nuevoINE
                })

                Spacer(modifier = Modifier.height(32.dp))

                val context = LocalContext.current
                val caseViewModel: CaseViewModel = viewModel()
                val caseData = mapOf(
                    "segundoFormulario" to true
                )
                // Botón de enviar información
                EnviarInformacionButton(
                    delito = delitoIngresado,
                    fiscalia = fiscaliaSeleccionada,
                    ine = ineIngresado,
                    onEnviar = { extraInfoData ->
                            caseViewModel.updateExtraInfoOfCase(caseId,extraInfoData)

                            caseViewModel.updateCase(caseId,caseData)
                            Toast.makeText(context, "Informacion agregada a su caso", Toast.LENGTH_LONG).show()
                            navController?.navigate("solicitud")

                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDelito(onDelitoChange: (String) -> Unit) {
    var delito by remember { mutableStateOf("") }

    TextField(
        value = delito,
        onValueChange = {
            delito = it
            onDelitoChange(it) // Devolver el valor ingresado en el campo
        },
        label = { Text("Delito") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFiscalia(onFiscaliaEntered: (String) -> Unit) {
    var fiscalia by remember { mutableStateOf("") }

    TextField(
        value = fiscalia,
        onValueChange = {
            fiscalia = it
            onFiscaliaEntered(it) // Devolver el valor ingresado
        },
        label = { Text("Fiscalía") },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCI(onIneChange: (String) -> Unit) {
    var ine by remember { mutableStateOf("") }

    TextField(
        value = ine,
        onValueChange = {
            ine = it
            onIneChange(it) // Devolver el valor ingresado
        },
        label = { Text("INE.") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

@Composable
fun EnviarInformacionButton(
    delito: String,
    fiscalia: String,
    ine: String,
    onEnviar: (Map<String, String>) -> Unit // Callback para manejar el envío
) {
    val context = LocalContext.current
    Button(
        onClick = {
            // Validar que ninguno de los campos esté vacío
            if (delito.isEmpty() || fiscalia.isEmpty() || ine.isEmpty() ||
                delito.isBlank() || fiscalia.isBlank() || ine.isBlank()) {
                Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Crear el mapa con los valores
                val extraInfoData = mapOf(
                    "crime" to delito,
                    "fiscalia" to fiscalia,
                    "ine" to ine
                )
                onEnviar(extraInfoData)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
    ) {
        Text(
            text = "Enviar Información",
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SegundoFormularioPreview() {
    ClinicaPenalTheme {
        SegundoFormulario(navController = rememberNavController(), caseId = "1")
    }
}
