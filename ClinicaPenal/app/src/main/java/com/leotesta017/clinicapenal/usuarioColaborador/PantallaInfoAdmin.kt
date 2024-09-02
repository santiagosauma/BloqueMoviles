package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.LabelCategoria
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.SearchBar
import com.leotesta017.clinicapenal.usuarioGeneral.CategoriesSection
import com.leotesta017.clinicapenal.usuarioGeneral.ServicesSection

@Composable
fun PantallaInfoAdmin(navController: NavController?) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TopBar()
                Spacer(modifier = Modifier.height(16.dp))
                InfoLegalSection()
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar("")
                Spacer(modifier = Modifier.height(16.dp))
                SortingVertically(
                    action1 = { LabelCategoria("Categoria") },
                    action2 = { navController?.navigate("modificar-info") },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategoriesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
                SortingVertically(
                    action1 = { LabelCategoria("Servicios") },
                    action2 = { navController?.navigate("modificar_servicios_info") },
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                ServicesSection(navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 95.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RoundedButton(
                icon = Icons.AutoMirrored.Filled.Chat,
                label = "JuriBot",
                onClick = { navController?.navigate("ReviewComentarios") }
            )
            RoundedButton(
                icon = Icons.Default.CalendarToday,
                label = "Solicitud de Cita",
                onClick = { navController?.navigate("crearsolicitud") }
            )
        }

        AdminBarraNav(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SortingVertically(
    action1: @Composable () -> Unit,
    action2: () -> Unit,
    navController: NavController?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        action1()
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = action2) {
            Text("Agregar Información")
        }
    }
}

@Composable
fun InfoLegalSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Información Legal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInfoPreview() {
    PantallaInfoAdmin(navController = null)
}
