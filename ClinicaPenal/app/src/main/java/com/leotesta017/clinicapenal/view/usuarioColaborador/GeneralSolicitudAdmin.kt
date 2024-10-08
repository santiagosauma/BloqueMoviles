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
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CaseUserAdminItem
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.leotesta017.clinicapenal.view.templatesPantallas.GenerarSolicitudPantallaTemplatenavController
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.CaseViewModel
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel


@Composable
fun GeneralSolicitudAdmin(navController: NavController?) {
    val caseViewModel: CaseViewModel = viewModel()
    val userId = UserIdData.userId
    val usuarioViewModel: UsuarioViewModel = viewModel()

    val casesList by usuarioViewModel.userCasesWithAppointments.collectAsState()

    LaunchedEffect(userId) {
        if (userId != null) {
            usuarioViewModel.fetchUserCasesWithLastAppointmentDetails(userId)
        }
    }

    val citasList by caseViewModel.unrepresentedCasesWithLastAppointment.collectAsState()

    LaunchedEffect(citasList){
        caseViewModel.fetchUnrepresentedCasesWithLastAppointment()
    }

    val representacionList = remember { mutableStateOf<List<Triple<Case,String,Boolean>>>(emptyList()) }

    val tempRepresentacionList = mutableListOf<Triple<Case,String,Boolean>>()

    casesList.forEach { caseDetails ->
        if ((caseDetails.first.studentAssigned == userId ||
                    caseDetails.first.lawyerAssigned == userId) &&
            caseDetails.first.represented)
        {
            tempRepresentacionList.add(caseDetails)
        }
    }

    representacionList.value = tempRepresentacionList

    GenerarSolicitudPantallaTemplatenavController(
        navController = navController,
        titulo1 = "Citas",
        items1 = citasList,
        itemComposable1 = { cita ->
            CaseUserAdminItem(
                case = cita,
                onDelete = { id -> caseViewModel.deleteCase(id) },
                confirmDeleteText = "¿Estás seguro de que deseas eliminar esta cita?",
                navController = navController,
                route = "actualizarcasos"
            )
        },
        titulo2 = "Casos Representación",
        items2 = representacionList.value,
        itemComposable2 = { representacion ->
            CaseUserAdminItem(
                case = representacion,
                onDelete = { id -> caseViewModel.deleteCase(id) },
                confirmDeleteText = "¿Estás seguro de que deseas eliminar este caso de representación?",
                navController = navController,
                route = "actualizarcasos"
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
