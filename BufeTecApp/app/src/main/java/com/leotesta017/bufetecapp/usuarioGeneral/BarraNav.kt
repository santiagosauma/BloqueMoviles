// BarraNav.kt
package com.leotesta017.bufetecapp.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.leotesta017.bufetecapp.ui.theme.BufeTecAppTheme

@Composable
fun BarraNav(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color(0xffffff))
            .padding(top=15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(icon = Icons.Default.Menu, text = "Inicio")
        BottomBarItem(icon = Icons.Default.Folder, text = "Solicitudes")
        BottomBarItem(icon = Icons.Default.Info, text = "Información")
    }
}

@Composable
fun BottomBarItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, backgroundColor: Color = Color.Transparent) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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
fun Preview() {
    BufeTecAppTheme {
        BarraNav()
    }
}