package com.leotesta017.clinicapenal.solicitud

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.CaseUserGenaralItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.NotificationItem
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.SearchBarHistorialSolicitudes
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.UsuarioViewModel


@Composable
fun GeneralSolicitud(
    navController: NavController?,
    usuarioViewModel: UsuarioViewModel = viewModel(),
) {
    val userId = UserIdData.userId ?: return

    LaunchedEffect(userId) {
        usuarioViewModel.fetchUserCasesWithLastAppointmentDetails(userId)
    }

// Observamos el estado de los casos y mostramos la información
    val userCases by usuarioViewModel.userCasesWithAppointments.collectAsState()

// Mostramos un mensaje de error si existe
    val error by usuarioViewModel.error.collectAsState()

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
                    casos = userCases,
                    isAdmin = false,
                    isUsuarioGeneral = true,
                    navController = navController,
                    onSearchTextChange = { searchQuery ->
                        isBusqueda = searchQuery.isNotBlank()
                    }
                )
            }

            if(!isBusqueda)
            {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Historial de Casos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Recorremos la lista de casos del usuario general
                items(userCases) { caso ->
                    CaseUserGenaralItem(
                        case = caso,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Puedes agregar una lista de notificaciones si fuera necesario
                items(emptyList<String>()) { notification ->
                    NotificationItem(notification)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

        Spacer(modifier = Modifier.height(32.dp))

        BarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}
