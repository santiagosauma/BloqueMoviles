package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.AgregarInfoTemplate
import com.leotesta017.clinicapenal.viewmodel.CategoryViewModel

@Composable
fun AgregarInfoAdmin(navController: NavController?) {
    val categoriaViewModel: CategoryViewModel = viewModel()

    AgregarInfoTemplate(
        navController = navController,
        titulo = "Agregar Información",
        textDescripcion = "",
        bottomBarContent = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        onAddClick = { nombre,descripcion, urlImagen, contenido ->


            val NCategoria = Categoria(
                        titulo = nombre,
                        descripcion = descripcion,
                        url_imagen = urlImagen,
                        contenido = contenido
            )

            categoriaViewModel.addCategoria(NCategoria)
            navController?.navigate("pantallainfoadmin")
        },
        onCancelClick = {
            navController?.navigate("pantallainfoadmin")
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewAgregarInfoAdmin() {
    AgregarInfoAdmin(navController = null)
}
