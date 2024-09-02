package com.leotesta017.clinicapenal.funcionesDeUsoGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme

@Composable
fun AdminBarraNav(navController: NavController?, modifier: Modifier = Modifier) {
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
            onClick = { navController?.navigate("pantallainfoadmin") }
        )
        AdminBottomBarItem(
            icon = Icons.Default.Folder,
            text = "Solicitudes",
            onClick = { navController?.navigate("generalsolicitudadmin") }
        )
        AdminBottomBarItem(
            icon = Icons.Default.Info,
            text = "Información",
            onClick = { navController?.navigate("informacionAdmin") }
        )
    }
}


@Composable
fun AdminBottomBarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(backgroundColor)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = text, tint = Color.Black)
        }
        Text(text = text, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdminBarraNav() {
    ClinicaPenalTheme {
        AdminBarraNav(navController = null)
    }
}