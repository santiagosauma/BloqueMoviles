package com.leotesta017.clinicapenal.view.loginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme

@Composable
fun Pantalla3(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = "Clínica Penal",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Justicia al alcance de todos: asesoría jurídica gratuita con el respaldo de estudiantes y expertos",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { navController.navigate("pantalla4") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFb4b9da),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Crear Cuenta")
        }


        Button(
            onClick = { navController.navigate("pantalla5") },
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-48).dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF002366))
        ) {
            Text(text = "Iniciar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla3Preview() {
    ClinicaPenalTheme {
        Pantalla3(navController = rememberNavController())
    }
}