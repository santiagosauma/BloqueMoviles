package com.leotesta017.clinicapenal.view.usuarioColaborador

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.usuarioGeneral.InputField
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel

@Composable
fun ModificarSegundoFormulario(navController: NavController?, caseId: String) {
    val context = LocalContext.current
    val caseViewModel: CaseViewModel = viewModel()

    val extrainfo by caseViewModel.lastExtraInfo.collectAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) } // Estado para controlar el dialogo
    var isFormModified by rememberSaveable { mutableStateOf(false) } // Estado para detectar cambios

    LaunchedEffect(caseId) {
        caseViewModel.fetchLastExtraInfo(caseId)
    }

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
                        // Mostrar el dialog si hay cambios sin guardar
                        if (isFormModified) {
                            showDialog = true
                        } else {
                            navController?.navigate("actualizarcasos/$caseId")
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Editar formulario de representación",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
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
                extrainfo?.let { extrainfo ->
                    EditarFormularioCompleto(
                        aFiscalia = extrainfo.fiscalia,
                        aCrime = extrainfo.crime,
                        aIne = extrainfo.ine,
                        aNUC = extrainfo.nuc,
                        aCarpetaInvestigacion = extrainfo.carpetaInvestigacion,
                        aCarpetaJudicial = extrainfo.carpetaJudicial,
                        aAFV = extrainfo.afv,
                        aPasswordFV = extrainfo.passwordFV,
                        aFiscalTitular = extrainfo.fiscalTitular,
                        aUnidadInvestigacion = extrainfo.unidadInvestigacion,
                        aDireccionUI = extrainfo.direccionUI,
                        onEnviar = { extraInfoData ->
                            // Actualizar extraInfo y el caso
                            caseViewModel.updateExtraInfoOfCase(caseId, extraInfoData)

                            // Mostrar el mensaje de éxito
                            Toast.makeText(context, "Información actualizada exitosamente", Toast.LENGTH_LONG).show()

                            // Navegar de vuelta a la pantalla de solicitud
                            navController?.navigate("actualizarcasos/$caseId")
                        },
                        onFormModified = { isModified ->
                            isFormModified = isModified // Actualizar el estado si hay cambios
                        }
                    )
                }
            }
        }
    )

    // Mostrar un diálogo si hay cambios no guardados
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Cambios sin guardar") },
            text = { Text(text = "Tienes cambios sin guardar. ¿Estás seguro de que deseas salir?") },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    navController?.navigate("actualizarcasos/$caseId") // Navegar sin guardar
                }) {
                    Text("Salir sin guardar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun EditarFormularioCompleto(
    aFiscalia: String,
    aCrime: String,
    aIne: String,
    aNUC: String,
    aCarpetaJudicial: String,
    aCarpetaInvestigacion: String,
    aAFV: String,
    aPasswordFV: String,
    aFiscalTitular: String,
    aUnidadInvestigacion: String,
    aDireccionUI:String,
    onFormModified: (Boolean) -> Unit, // Callback para detectar cambios
    onEnviar: (Map<String, String>) -> Unit
) {
    var nFiscalia by remember { mutableStateOf(aFiscalia.ifEmpty { "" }) }
    var nCrime by remember { mutableStateOf(aCrime.ifEmpty { "" }) }
    var nINE by remember { mutableStateOf(aIne.ifEmpty { "" }) }
    var nNUC by remember { mutableStateOf(aNUC.ifEmpty { "" }) } // Número Único de Causa (NUC)
    var nCarpetaJudicial by remember { mutableStateOf(aCarpetaJudicial.ifEmpty { "" }) }
    var nCarpetaInvestigacion by remember { mutableStateOf(aCarpetaInvestigacion.ifEmpty { "" }) }
    var nAFV by remember { mutableStateOf(aAFV.ifEmpty { "" }) } // Acceso a Fiscalía Virtual
    var nPasswordFV by remember { mutableStateOf(aPasswordFV.ifEmpty { "" }) } // Contraseña de Fiscalía Virtual
    var nFiscalTitular by remember { mutableStateOf(aFiscalTitular.ifEmpty { "" }) }
    var nUnidadInvestigacion by remember { mutableStateOf(aUnidadInvestigacion.ifEmpty { "" }) }
    var nDireccionUI by remember { mutableStateOf(aDireccionUI.ifEmpty { "" }) } // Dirección de la Unidad de Investigación

    // Detectar cambios en los campos del formulario
    fun detectChanges(): Boolean {
        return aFiscalia != nFiscalia || aCrime != nCrime || aIne != nINE ||
                aNUC != nNUC || aCarpetaJudicial != nCarpetaJudicial ||
                aCarpetaInvestigacion != nCarpetaInvestigacion || aAFV != nAFV ||
                aPasswordFV != nPasswordFV || aFiscalTitular != nFiscalTitular ||
                aUnidadInvestigacion != nUnidadInvestigacion || aDireccionUI != nDireccionUI
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        InputField(
            label = "Fiscalía",
            value = nFiscalia,
            onValueChange = {
                nFiscalia = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para Delito
        InputField(
            label = "Delito (Crime)",
            value = nCrime,
            onValueChange = {
                nCrime = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para INE
        InputField(
            label = "INE",
            value = nINE,
            onValueChange = {
                nINE = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Información Detallada",
            style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campos adicionales opcionales
        InputField(
            label = "Número Único de Causa (NUC)",
            value = nNUC,
            onValueChange = {
                nNUC = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Carpeta Judicial",
            value = nCarpetaJudicial,
            onValueChange = {
                nCarpetaJudicial = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Carpeta de Investigación",
            value = nCarpetaInvestigacion,
            onValueChange = {
                nCarpetaInvestigacion = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Acceso a Fiscalía Virtual (AFV)",
            value = nAFV,
            onValueChange = {
                nAFV = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Contraseña de Fiscalía Virtual",
            value = nPasswordFV,
            onValueChange = {
                nPasswordFV = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Fiscal Titular",
            value = nFiscalTitular,
            onValueChange = {
                nFiscalTitular = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Unidad de Investigación",
            value = nUnidadInvestigacion,
            onValueChange = {
                nUnidadInvestigacion = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Dirección de la Unidad de Investigación",
            value = nDireccionUI,
            onValueChange = {
                nDireccionUI = it
                onFormModified(detectChanges()) // Detectar cambios
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botón de enviar la información
        Button(
            onClick = {
                val formData = mapOf(
                    "fiscalia" to nFiscalia,
                    "crime" to nCrime,
                    "ine" to nINE,
                    "nuc" to nNUC,
                    "carpetaJudicial" to nCarpetaJudicial,
                    "carpetaInvestigacion" to nCarpetaInvestigacion,
                    "afv" to nAFV,
                    "passwordFV" to nPasswordFV,
                    "fiscalTitular" to nFiscalTitular,
                    "unidadInvestigacion" to nUnidadInvestigacion,
                    "direccionUI" to nDireccionUI
                )
                onEnviar(formData)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Información", color = Color.White)
        }
    }
}
