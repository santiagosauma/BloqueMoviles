package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaComentarTemplate
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel

@Composable
fun ComentarioScreen(navController: NavController?, caseId: String) {
    val comentarioViewModel: ComentarioViewModel = viewModel()
    PantallaComentarTemplate(
        navController = navController,
        title = "Comentario",
        caseId = caseId,
        route =  "actualizarcasos",
        onAddComment = { contenido, isUrgent, userId, case_Id ->
            comentarioViewModel.addNewComentarioToCase(
                contenido = contenido,
                important = isUrgent,
                userId = userId,
                caseId = case_Id
            )
        },
        bottomBarNav = {
            AdminBarraNav(
                navController = navController,
                modifier = Modifier.fillMaxWidth()
            )


        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewComentarioScreen() {
    ComentarioScreen(navController = null, "1")
}
