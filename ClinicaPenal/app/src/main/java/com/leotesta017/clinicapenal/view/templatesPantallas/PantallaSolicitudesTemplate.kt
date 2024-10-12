package com.leotesta017.clinicapenal.view.templatesPantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CaseUserAdminItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBarHistorialSolicitudes
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GenerarSolicitudPantallaTemplatenavController(
    navController: NavController?,
    caseViewModel: CaseViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
    userId: String?,
    isAdmin: Boolean, // Flag para determinar si es Admin o Estudiante
    barraNavComposable: @Composable () -> Unit
) {
    // Obtener las listas desde el ViewModel
    val casesList by usuarioViewModel.userCasesWithAppointments.collectAsState()
    val citasList by caseViewModel.unrepresentedCasesWithLastAppointment.collectAsState()

    // Efecto para cargar los casos del usuario
    LaunchedEffect(userId) {
        if (userId != null) {
            usuarioViewModel.fetchUserCasesWithLastAppointmentDetails(userId)
            caseViewModel.fetchUnrepresentedCasesWithLastAppointment()
        }
    }

    // Lista temporal de casos de representación
    val representacionList = remember { mutableStateOf<List<Triple<Case, String, Boolean>>>(emptyList()) }
    val tempRepresentacionList = mutableListOf<Triple<Case, String, Boolean>>()

    // Filtrar los casos de representación
    casesList.forEach { caseDetails ->
        if ((caseDetails.first.studentAssigned == userId ||
            caseDetails.first.lawyerAssigned == userId) &&
            caseDetails.first.represented
        ) {
            tempRepresentacionList.add(caseDetails)
        }
    }
    representacionList.value = tempRepresentacionList

    var isBusqueda by remember { mutableStateOf(false) }

    // Pantalla con las listas filtradas
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBarHistorialSolicitudes(
                    searchText = "",
                    onSearchStarted = {isStarted ->
                        isBusqueda = isStarted
                    },
                    casos = (casesList + citasList).distinctBy { it.first.case_id},
                    isAdmin = isAdmin,
                    isUsuarioGeneral = false,
                    navController = navController,
                    onSearchTextChange = { searchQuery ->
                        isBusqueda = searchQuery.isNotBlank()
                    }
                )
            }

            if (!isBusqueda)
            {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Citas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Recorremos la lista de citas
                items(citasList) { cita ->
                    CaseUserAdminItem(
                        case = cita,
                        onDelete = { id ->
                            if (isAdmin) {
                                caseViewModel.discardCase(id)
                            } else {
                                // Estudiante, manejar diferente si es necesario
                            }
                        },
                        confirmDeleteText = "¿Estás seguro de que deseas eliminar esta cita?",
                        navController = navController,
                        route = if (isAdmin) "actualizarcasos" else "detallecasoestudiante",
                        isAdmin = isAdmin
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Casos Representación",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Recorremos la lista de casos de representación
                items(representacionList.value) { representacion ->
                    CaseUserAdminItem(
                        case = representacion,
                        onDelete = { id ->
                            if (isAdmin) {
                                caseViewModel.discardCase(id)
                            } else {
                                //El estudiante no podria eliminar casos
                            }
                        },
                        confirmDeleteText = "¿Estás seguro de que deseas eliminar este caso de representación?",
                        navController = navController,
                        route = if (isAdmin) "actualizarcasos" else "detallecasoestudiante",
                        isAdmin = isAdmin
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))


        barraNavComposable()
    }
}

