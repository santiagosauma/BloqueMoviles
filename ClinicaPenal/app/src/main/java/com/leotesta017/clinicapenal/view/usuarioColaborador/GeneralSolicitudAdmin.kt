package com.leotesta017.clinicapenal.view.usuarioColaborador


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CaseItemTemplate
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel


@Composable
fun GeneralSolicitudAdmin(navController: NavController?) {
    val caseViewModel: CaseViewModel = viewModel()
    val userId = UserIdData.userId
    val usuarioViewModel: UsuarioViewModel = viewModel()

// Obtenemos la lista de casos completos del usuario
    val casesList by usuarioViewModel.userCases.collectAsState()

// Llamamos la función para obtener los casos del usuario con detalles al iniciar
    LaunchedEffect(userId) {
        if (userId != null) {
            // Obtener casos completos con sus detalles
            usuarioViewModel.fetchUserCasesWithDetails(userId)
        }
    }

// Filtrar las listas de citas y casos de representación
    val citasList = remember { mutableStateOf<List<Case>>(emptyList()) }
    val representacionList = remember { mutableStateOf<List<Case>>(emptyList()) }

// Separar los casos entre citas y representaciones
    val tempCitasList = mutableListOf<Case>()
    val tempRepresentacionList = mutableListOf<Case>()

    // Llenar las listas temporales
    casesList.forEach { caseDetails ->
        Log.d("CaseDebug", "Case ID: ${caseDetails.case_id}, isRepresented: ${caseDetails.isRepresented}")
        if (caseDetails.lawyerAssigned == userId) {
            if (caseDetails.isRepresented == true) {
                tempRepresentacionList.add(caseDetails)
            } else {
                tempCitasList.add(caseDetails)
            }
        }
    }

    // Asignar las listas actualizadas al estado una vez
    citasList.value = tempCitasList
    representacionList.value = tempRepresentacionList

    // Llamamos a la función con las listas filtradas y los Composables adecuados
    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        titulo1 = "Citas",
        items1 = citasList.value,  // Pasamos la lista de citas
        itemComposable1 = { cita ->
            CaseItemTemplate(
                case = cita, // Pasamos el item de tipo Case
                onDelete = { id -> caseViewModel.deleteCase(id) },
                confirmDeleteText = "¿Estás seguro de que deseas eliminar esta cita?"
            )
        },
        titulo2 = "Casos Representación",
        items2 = representacionList.value,  // Pasamos la lista de casos de representación
        itemComposable2 = { representacion ->
            CaseItemTemplate(
                case = representacion, // Pasamos el item de tipo Case
                onDelete = { id -> caseViewModel.deleteCase(id) },
                confirmDeleteText = "¿Estás seguro de que deseas eliminar este caso de representación?"
            )
        },
        barraNavComposable = {
            Box(modifier = Modifier.fillMaxSize()) {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GeneralSolicitudAdminPreview() {
    ClinicaPenalTheme {
        GeneralSolicitudAdmin(navController = rememberNavController())
    }
}
//route = "actualizarcasos"