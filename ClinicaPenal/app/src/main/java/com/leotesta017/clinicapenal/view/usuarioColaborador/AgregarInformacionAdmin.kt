package com.leotesta017.clinicapenal.view.usuarioColaborador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.view.funcionesDeUsoGeneral.TextEditor
import com.leotesta017.clinicapenal.view.templatesPantallas.PantallaAgregarInformacionTemplate


@Composable
fun AgregarInfoAdmin(navController: NavController?) {

    var nombre by remember { mutableStateOf("") }

    PantallaAgregarInformacionTemplate(
        navController = navController,
        titulo = "Agregar Información",
        textDescripcion = "Inserte información ...",
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RoundedButton(
                    icon = Icons.Default.Edit,
                    label = "Añadir",
                    onClick = { /* Lógica para añadir */ }
                )
                RoundedButton(
                    icon = Icons.Default.Delete,
                    label = "Cancelar",
                    onClick = { /* Lógica para cancelar */ }
                )
            }
            AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        content = { paddingValues ->
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Contenido",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 8.dp, start = 10.dp)
            )

        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewAgregarInfoAdmin() {
    AgregarInfoAdmin(navController = null)
}
