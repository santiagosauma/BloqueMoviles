package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaComentarTemplate
import com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario.ComentarioViewModel

@Composable
fun EditarBorrarComentarioAdmin(navController: NavController?, caseId: String, comentarioId:String) {
    val comentarioViewModel: ComentarioViewModel = viewModel()

    val comentario by comentarioViewModel.comentario.collectAsState()
    val isLoading = comentario == null

    LaunchedEffect(Unit) {
        comentarioViewModel.fetchComentario(comentarioId)
    }

    if(isLoading)
    {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
    else {
        PantallaComentarTemplate(
            navController = navController,
            title = "Editar Comentario",
            caseId = caseId,
            route = "actualizarcasos",
            comentarioIncial = comentario?.contenido,
            isUrgentInitial = comentario?.important,
            isEditing = true,
            comentarioId = comentarioId,
            onAddOrEditComment = { contenido, isUrgent, userId, comentario_id ->
                val dataComentario = mapOf(
                    "contenido" to contenido,
                    "important" to isUrgent,
                )
                comentarioViewModel.updateComentario(comentario_id, dataComentario)
                navController?.navigate("editarComentarioAdmin/${caseId}/${comentarioId}")
            },
            bottomBarNav = {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            onDeleteComment = { comentario_id ->
                comentarioViewModel.deleteComentario(comentario_id,caseId )
                navController?.navigate("actualizarcasos/${caseId}")
            }
        )
    }
}