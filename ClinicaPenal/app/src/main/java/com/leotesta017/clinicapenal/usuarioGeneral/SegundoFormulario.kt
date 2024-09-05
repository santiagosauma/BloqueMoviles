package com.leotesta017.clinicapenal.usuarioGeneral

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar
import com.leotesta017.clinicapenal.ui.theme.ClinicaPenalTheme

@Composable
fun SegundoFormulario(navController: NavController?) {
    Scaffold(
        topBar = {
            Column {
                TopBar()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 3.dp)
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Formulario Representación",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            BarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputDelito()
                Spacer(modifier = Modifier.height(16.dp))
                DropdownFiscalia()
                Spacer(modifier = Modifier.height(16.dp))
                InputCI()
                Spacer(modifier = Modifier.height(32.dp))
                EnviarInformacionButton()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDelito() {
    var delito by remember { mutableStateOf("") }

    TextField(
        value = delito,
        onValueChange = { delito = it },
        label = { Text("Delito") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownFiscalia() {
    var selectedFiscalia by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val fiscalias = listOf("Fiscalía 1", "Fiscalía 2", "Fiscalía 3")

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = selectedFiscalia,
            onValueChange = { selectedFiscalia = it },
            label = { Text("Fiscalía") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF2F2F2),
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.Blue
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            fiscalias.forEach { fiscalia ->
                DropdownMenuItem(
                    onClick = {
                        selectedFiscalia = fiscalia
                        expanded = false
                    },
                    text = {
                        Text(text = fiscalia, color = Color.Black)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCI() {
    var ci by remember { mutableStateOf("") }

    TextField(
        value = ci,
        onValueChange = { ci = it },
        label = { Text("C.I.") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFF2F2F2),
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

@Composable
fun EnviarInformacionButton() {
    Button(
        onClick = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F8C)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = "Enviar Información",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SegundoFormularioPreview() {
    ClinicaPenalTheme {
        SegundoFormulario(navController = rememberNavController())
    }
}
