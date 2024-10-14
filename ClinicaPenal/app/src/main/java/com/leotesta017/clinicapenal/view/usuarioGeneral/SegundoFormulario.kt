package com.leotesta017.clinicapenal.view.usuarioGeneral

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
                val context = LocalContext.current
                val caseViewModel: CaseViewModel = viewModel()
                FormularioCompleto(onEnviar = { extraInfoData ->
                    val caseData = mapOf("segundoFormulario" to true)

                    // Actualizar extraInfo y el caso
                    caseViewModel.updateExtraInfoOfCase(caseId, extraInfoData)
                    caseViewModel.updateCase(caseId, caseData)

                    // Mostrar el mensaje de éxito
                    Toast.makeText(context, "Información agregada. Gracias por responder :)", Toast.LENGTH_LONG).show()

                    // Navegar de vuelta a la pantalla de solicitud
                    navController?.navigate("solicitud")
                })
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Row {
                Text(label)
                if (isError) {
                    Text(" *", color = Color.Red) // Indicador de campo obligatorio
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = if (isError) Color.Red else Color.Blue,
            unfocusedIndicatorColor = if (isError) Color.Red else Color.Gray,
            cursorColor = Color.Blue
        ),
        isError = isError
    )
}

@Composable
fun FormularioCompleto(onEnviar: (Map<String, String>) -> Unit) {
    var fiscalia by remember { mutableStateOf("") }
    var crime by remember { mutableStateOf("") } // Delito
    var ine by remember { mutableStateOf("") }
    var nuc by remember { mutableStateOf("") } // Número Único de Causa (NUC)
    var carpetaJudicial by remember { mutableStateOf("") }
    var carpetaInvestigacion by remember { mutableStateOf("") }
    var afv by remember { mutableStateOf("") } // Acceso a Fiscalía Virtual
    var passwordFV by remember { mutableStateOf("") } // Contraseña de Fiscalía Virtual
    var fiscalTitular by remember { mutableStateOf("") }
    var unidadInvestigacion by remember { mutableStateOf("") }
    var direccionUI by remember { mutableStateOf("") } // Dirección de la Unidad de Investigación

    // Variables de control para mostrar el error en los campos obligatorios
    var showErrorFiscalia by remember { mutableStateOf(false) }
    var showErrorCrime by remember { mutableStateOf(false) }
    var showErrorIne by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {
        // Campo para Fiscalía (obligatorio)
        InputField(
            label = "Fiscalía",
            value = fiscalia,
            onValueChange = { fiscalia = it },
            isError = showErrorFiscalia
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Delito (obligatorio)
        InputField(
            label = "Delito (Crime)",
            value = crime,
            onValueChange = { crime = it },
            isError = showErrorCrime
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para INE (obligatorio)
        InputField(
            label = "INE",
            value = ine,
            onValueChange = { ine = it },
            isError = showErrorIne
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Información Detallada",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Si cuenta con esta información, favor de proporcionarla",
            style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campos adicionales opcionales
        InputField(label = "Número Único de Causa (NUC)",
            value = nuc, onValueChange = { nuc = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Carpeta Judicial",
            value = carpetaJudicial, onValueChange = { carpetaJudicial = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Carpeta de Investigación",
            value = carpetaInvestigacion, onValueChange = { carpetaInvestigacion = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Acceso a Fiscalía Virtual (AFV)",
            value = afv, onValueChange = { afv = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Contraseña de Fiscalía Virtual",
            value = passwordFV, onValueChange = { passwordFV = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Fiscal Titular",
            value = fiscalTitular, onValueChange = { fiscalTitular = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Unidad de Investigación",
            value = unidadInvestigacion, onValueChange = { unidadInvestigacion = it })
        Spacer(modifier = Modifier.height(16.dp))

        InputField(label = "Dirección de la Unidad de Investigación",
            value = direccionUI, onValueChange = { direccionUI = it })
        Spacer(modifier = Modifier.height(32.dp))

        // Botón de enviar la información
        Button(
            onClick = {
                // Resetear los indicadores de error
                showErrorFiscalia = fiscalia.isBlank() || fiscalia.isEmpty()
                showErrorCrime = crime.isBlank() || fiscalia.isEmpty()
                showErrorIne = ine.isBlank() || fiscalia.isEmpty()

                if (crime.isBlank() || fiscalia.isBlank() || ine.isBlank() ||
                    crime.isEmpty() || fiscalia.isEmpty() || ine.isEmpty() ) {

                    Toast.makeText(context, "Por favor, completa los campos obligatorios.", Toast.LENGTH_SHORT).show()

                } else {
                    val formData = mapOf(
                        "fiscalia" to fiscalia,
                        "crime" to crime,
                        "ine" to ine,
                        "nuc" to nuc,
                        "carpetaJudicial" to carpetaJudicial,
                        "carpetaInvestigacion" to carpetaInvestigacion,
                        "afv" to afv,
                        "passwordFV" to passwordFV,
                        "fiscalTitular" to fiscalTitular,
                        "unidadInvestigacion" to unidadInvestigacion,
                        "direccionUI" to direccionUI
                    )
                    onEnviar(formData) // Enviar el formulario
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Información", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SegundoFormularioPreview() {
    ClinicaPenalTheme {
        SegundoFormulario(navController = rememberNavController(), caseId = "1")
    }
}
