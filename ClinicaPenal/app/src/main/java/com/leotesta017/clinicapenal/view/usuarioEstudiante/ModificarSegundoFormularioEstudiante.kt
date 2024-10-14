package com.leotesta017.clinicapenal.view.usuarioEstudiante

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.view.usuarioColaborador.EditarFormularioCompleto
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel

@Composable
fun ModificarSegundoFormularioEstudiante(navController: NavController?, caseId: String) {
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
                            navController?.navigate("detallecasoestudiante/$caseId")
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
            EstudiantesBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
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
                            navController?.navigate("detallecasoestudiante/$caseId")
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
                    navController?.navigate("detallecasoestudiante/$caseId") // Navegar sin guardar
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