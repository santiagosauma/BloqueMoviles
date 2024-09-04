package com.leotesta017.clinicapenal.funcionesDeUsoGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BarraNav(navController: NavController?, modifier: Modifier = Modifier) {
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()?.value
    val currentDestination = currentBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            icon = Icons.Default.Menu,
            text = "Inicio",
            isSelected = currentDestination == "pantallainfocategoriasgeneral",
            onClick = { navController?.navigate("pantallainfocategoriasgeneral") }
        )
        BottomBarItem(
            icon = Icons.Default.Folder,
            text = "Solicitudes",
            isSelected = currentDestination == "solicitud",
            onClick = { navController?.navigate("solicitud") }
        )
        BottomBarItem(
            icon = Icons.Default.Info,
            text = "Información",
            isSelected = currentDestination == "pantallainformacionclinica",
            onClick = { navController?.navigate("pantallainformacionclinica") }
        )
    }
}

@Composable
fun BottomBarItem(icon: ImageVector, text: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = if (isSelected) Color(0xFF0B1F8C) else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) Color(0xFFFFFFFF) else Color.Black,
            )
        }
        Text(
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun PreviewBarraNav(){
    BarraNav(navController = null)
}