package com.leotesta017.bufetecapp.usuarioEstudiante

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leotesta017.bufetecapp.ui.theme.BufeTecAppTheme

@Composable
fun Pantalla12() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Pantalla de Estudiante", style = MaterialTheme.typography.headlineSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla12Preview() {
    BufeTecAppTheme {
        Pantalla12()
    }
}