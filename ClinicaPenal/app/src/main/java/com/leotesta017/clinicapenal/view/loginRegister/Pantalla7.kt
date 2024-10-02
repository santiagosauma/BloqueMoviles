package com.leotesta017.clinicapenal.view.loginRegister

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme

@Composable
fun Pantalla7(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Text(text = "Soy Clínica Penal", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("pantalla8") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFb4b9da),
                contentColor = Color.Black
            )
        ) {
            Text(text = "Estudiante")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("pantalla9") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF303665))
        ) {
            Text(text = "Colaborador")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla7Preview() {
    ClinicaPenalTheme {
        Pantalla7(navController = rememberNavController())
    }
}