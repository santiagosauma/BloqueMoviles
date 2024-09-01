package com.leotesta017.bufetecapp.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.bufetecapp.funcionesDeUsoGeneral.TopBar

@Composable
fun DetalleInfo(navController: NavController?) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BarraNav(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1A237E))
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection("Robo y Hurto", navController)
            Spacer(modifier = Modifier.height(16.dp))

            // Secciones con títulos
            SectionTitle("Descripción")
            Spacer(modifier = Modifier.height(8.dp))
            SectionContent("Descripción general del tema seleccionado.")
            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Ejemplos Comunes")
            Spacer(modifier = Modifier.height(8.dp))
            SectionContent("Ejemplo 1\nEjemplo 2\nEjemplo 3")
            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Consecuencias Legales")
            Spacer(modifier = Modifier.height(8.dp))
            SectionContent("Descripción de las consecuencias legales relacionadas.")
            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Recursos")
            Spacer(modifier = Modifier.height(8.dp))
            SectionContent("Recursos adicionales:\n- Recurso 1\n- Recurso 2")
        }
    }
}

@Composable
fun HeaderSection(title: String, navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f) 
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController?.navigate("crearsolicitud") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Solicitud de Cita",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Solicitud de Cita", color = Color.White)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun SectionContent(content: String) {
    Text(
        text = content,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DetalleInfoPreview() {
    DetalleInfo(navController = null)
}
