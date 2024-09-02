package com.leotesta017.clinicapenal.loginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme

@Composable
fun Pantalla9(navController: NavController) {
    var nip by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    val nipCorrecto = "1010"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
        }

        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Text(text = "Ingresa NIP de Colaborador", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 16.dp))

        OutlinedTextField(
            value = nip,
            onValueChange = { nip = it },
            label = { Text("NIP") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                if (nip == nipCorrecto) {
                    navController.navigate("pantalla10")
                } else {
                    mensajeError = "NIP incorrecto. Inténtalo de nuevo."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue, // Color personalizado
                contentColor = Color.White // Texto negro
            )
        ) {
            Text(text = "Acceder")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla9Preview() {
    ClinicaPenalTheme {
        Pantalla9(navController = rememberNavController())
    }
}