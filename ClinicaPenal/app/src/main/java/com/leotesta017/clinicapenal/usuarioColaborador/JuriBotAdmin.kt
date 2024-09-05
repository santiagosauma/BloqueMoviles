package com.leotesta017.clinicapenal.usuarioColaborador

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.AdminBarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.BarraNav
import com.leotesta017.clinicapenal.funcionesDeUsoGeneral.TopBar

@Composable
fun JuriBotAdmin(navController: NavController?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            TopBar()

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                IconButton(onClick = {
                    navController?.navigate("pantallainfocategoriasgeneral")
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
                Text(
                    text = "JuriBot",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            MessageBubble(
                message = "¡Hola!, soy JuriBot, ¿En qué puedo ayudarte hoy?",
                isUserMessage = false
            )

            Spacer(modifier = Modifier.weight(1f))

            MessageInputBar()
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
fun MessageBubble(message: String, isUserMessage: Boolean) {
    val backgroundColor = if (isUserMessage) Color.LightGray else Color.Blue
    val textColor = if (isUserMessage) Color.Black else Color.White
    val alignment = if (isUserMessage) Alignment.CenterEnd else Alignment.CenterStart
    val cornerShape = if (isUserMessage) {
        RoundedCornerShape(16.dp)
    } else {
        RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp,
            bottomStart = 4.dp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = cornerShape)
                .padding(16.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(
                text = message,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputBar() {
    val inputText = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            placeholder = { Text("Enviar Mensaje...") },
            trailingIcon = {
                IconButton(onClick = {
                    inputText.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Enviar mensaje"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(24.dp))
                .background(Color.White, shape = RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JuriBotAdminPreview() {
    JuriBotAdmin(navController = null)
}
