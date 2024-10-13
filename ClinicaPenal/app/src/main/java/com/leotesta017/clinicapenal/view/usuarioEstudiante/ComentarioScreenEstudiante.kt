package com.leotesta017.clinicapenal.view.usuarioEstudiante

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.EstudiantesBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaComentarTemplate
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel

@Composable
fun ComentarioScreenEstudiante(navController: NavController?, caseId: String) {
    val comentarioViewModel: ComentarioViewModel = viewModel()
    PantallaComentarTemplate(
        navController = navController,
        title = "Comentario",
        caseId = caseId,
        route =  "detallecasoestudiante",
        onAddComment = { contenido, isUrgent, userId, case_Id ->
            comentarioViewModel.addNewComentarioToCase(
                contenido = contenido,
                important = isUrgent,
                userId = userId,
                caseId = case_Id
            )
        },
        bottomBarNav = {
            EstudiantesBarraNav(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )


        }
    )
}