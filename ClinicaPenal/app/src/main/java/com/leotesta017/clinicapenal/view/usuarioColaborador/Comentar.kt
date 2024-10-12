package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaComentarTemplate

@Composable
fun ComentarioScreen(navController: NavController?, caseId: String) {
    PantallaComentarTemplate(
        navController = navController,
        title = "Comentario",
        bottomBarNav = {
            Box(modifier = Modifier.fillMaxSize())
            {
                AdminBarraNav(
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewComentarioScreen() {
    ComentarioScreen(navController = null, "1")
}
