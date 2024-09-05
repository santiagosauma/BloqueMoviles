package com.leotesta017.clinicapenal.funcionesDeUsoGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AdminBarraNav(navController: NavController?, modifier: Modifier = Modifier) {
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()?.value
    val currentDestination = currentBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .background(Color.White)
            .padding(top = 15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AdminBottomBarItem(
            icon = Icons.Default.Menu,
            text = "Inicio",
            isSelected = currentDestination == "pantallainfoadmin",
            onClick = { navController?.navigate("pantallainfoadmin") }
        )
        AdminBottomBarItem(
            icon = Icons.Default.Folder,
            text = "Solicitudes",
            isSelected = currentDestination == "generalsolicitudadmin",
            onClick = { navController?.navigate("generalsolicitudadmin") }
        )
        AdminBottomBarItem(
            icon = Icons.Default.Info,
            text = "Información",
            isSelected = currentDestination == "",
            onClick = { navController?.navigate("") }
        )
    }
}

@Composable
fun AdminBottomBarItem(icon: ImageVector, text: String, isSelected: Boolean, onClick: () -> Unit) {
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


@Preview(showBackground = true)
@Composable
fun PreviewAdminBarraNav() {
    AdminBarraNav(navController = null)

}