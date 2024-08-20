package com.leotesta017.bufetecapp.usuarioColaborador

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
fun Pantalla13() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Pantalla de Colaborador", style = MaterialTheme.typography.headlineSmall)
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla13Preview() {
    BufeTecAppTheme {
        Pantalla13()
    }
}