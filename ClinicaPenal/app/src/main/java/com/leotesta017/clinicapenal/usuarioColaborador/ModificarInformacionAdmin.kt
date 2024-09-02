package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.RoundedButton
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar


@Composable
fun ModificarInfoAdmin(navController: NavController?) {

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
                        text = "Modificar Información",
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
                        icon = Icons.Default.Save,
                        label = "Guardar",
                        onClick = {  }
                    )
                    RoundedButton(
                        icon = Icons.Default.Delete,
                        label = "Descartar",
                        onClick = {  }
                    )
                }
                BarraNav(navController = navController, modifier = Modifier.fillMaxWidth())
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
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RichTextEditor()
                }
            }
        }
    )
}



@Composable
fun RichTextEditor() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }
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

        BasicTextField(
            value = textState,
            onValueChange = { value ->
                textState = value.copy(
                    annotatedString = buildAnnotatedString {
                        applyStyle(this, value.text, isBold, isItalic, isBullet)
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color.LightGray)
                .padding(8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
        )
    }
}

fun applyStyle(
    builder: AnnotatedString.Builder,
    text: String,
    isBold: Boolean,
    isItalic: Boolean,
    isBullet: Boolean
) {
    if (isBullet) {
        builder.append("• ")
    }
    builder.withStyle(
        style = SpanStyle(
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal
        )
    ) {
        builder.append(text)
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewModificarInfoAdmin() {
    ModificarInfoAdmin(navController = null)
}