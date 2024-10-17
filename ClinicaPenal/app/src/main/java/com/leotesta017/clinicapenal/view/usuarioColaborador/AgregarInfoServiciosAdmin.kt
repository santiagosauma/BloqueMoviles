package com.leotesta017.clinicapenal.view.usuarioColaborador


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.model.Servicio
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.templatesPantallas.AgregarInfoTemplate
import com.leotesta017.clinicapenal.viewmodel.ServicioViewModel

@Composable
fun AgregarServiciosInfoAdmin(navController: NavController?) {
    val servicioViewModel: ServicioViewModel = viewModel()
    AgregarInfoTemplate(
        navController = navController,
        titulo = "Agregar Servicio",
        textDescripcion = "",
        bottomBarContent = {
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        onAddClick = { nombre,descripcion, urlImagen, contenido ->
                val NServicio = Servicio(
                    titulo = nombre,
                    descripcion = descripcion,
                    url_imagen = urlImagen,
                    contenido = contenido
                )
            servicioViewModel.addServicio(NServicio)
            navController?.navigate("pantallainfoadmin")
        },
        onCancelClick = {
            navController?.navigate("pantallainfoadmin")
        }
    )
}




@Preview(showBackground = true)
@Composable
fun PreviewAgregarServiciosInfoAdmin() {
    AgregarServiciosInfoAdmin(navController = null)
}
