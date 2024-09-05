package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar

@Composable
fun ModificarServiciosAdmin(navController: NavController?) {

    var nombre by remember { mutableStateOf("") }

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
                        text = "Modificar Servicio",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    RoundedButton(
                        icon = Icons.Default.Edit,
                        label = "Guardar",
                        onClick = {  }
                    )
                    RoundedButton(
                        icon = Icons.Default.Delete,
                        label = "Descartar",
                        onClick = {  }
                    )
                }
                AdminBarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Contenido",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(top = 8.dp, start = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RichTextEditorServicio()
                }
            }
        }
    )
}



@Composable
fun RichTextEditorServicio() {
    var textState by remember {
        mutableStateOf(
            TextFieldValue(
                "Asesoría Legal\n\n" +
                        "La asesoría legal es un servicio que proporciona orientación y asistencia en asuntos legales. Este servicio es ofrecido por abogados profesionales que brindan consejo sobre derechos legales, obligaciones y posibles cursos de acción en situaciones legales.\n\n" +
                        "Beneficios de la Asesoría Legal:\n" +
                        "• Resolución rápida de conflictos legales\n" +
                        "• Evitar costosos litigios\n" +
                        "• Conocimiento de derechos y deberes\n" +
                        "• Orientación en procedimientos legales complejos\n\n" +
                        "Con la asesoría legal, puede obtener una mejor comprensión de sus derechos y tomar decisiones informadas antes de proceder con cualquier acción legal."
            )
        )
    }

    var isBold by remember { mutableStateOf(false) }
    var isItalic by remember { mutableStateOf(false) }
    var isBullet by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { isBold = !isBold }) {
                Icon(
                    imageVector = Icons.Default.FormatBold,
                    contentDescription = "Bold",
                    tint = if (isBold) Color.Blue else Color.Gray
                )
            }
            IconButton(onClick = { isItalic = !isItalic }) {
                Icon(
                    imageVector = Icons.Default.FormatItalic,
                    contentDescription = "Italic",
                    tint = if (isItalic) Color.Blue else Color.Gray
                )
            }
            IconButton(onClick = { isBullet = !isBullet }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                    contentDescription = "Bullet List",
                    tint = if (isBullet) Color.Blue else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            BasicTextField(
                value = textState,
                onValueChange = { value ->
                    textState = value.copy(
                        annotatedString = buildAnnotatedString {
                            applyStyle(this, value.text, isBold, isItalic, isBullet)
                        }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewModificarServiciosAdmin() {
    ModificarServiciosAdmin(navController = null)
}