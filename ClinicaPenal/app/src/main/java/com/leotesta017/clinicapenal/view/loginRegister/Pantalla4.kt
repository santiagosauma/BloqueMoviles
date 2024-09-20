package com.leotesta017.clinicapenal.view.loginRegister

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Pantalla4(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repetirContrasena by remember { mutableStateOf("") }
    var terminosAceptados by remember { mutableStateOf(false) }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val correoValido = correo.endsWith("@gmail.com") || correo.endsWith("@outlook.com") || correo.endsWith("@tec.mx")
    val contrasenaValida = contrasena.isNotBlank() &&
            contrasena == repetirContrasena &&
            !contrasena.contains(' ') && !contrasena.contains('.')

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Registro",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            isError = !correoValido,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                    Icon(imageVector = if (contrasenaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = repetirContrasena,
            onValueChange = { repetirContrasena = it },
            label = { Text("Repetir Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = terminosAceptados,
                onCheckedChange = { terminosAceptados = it },
                colors = CheckboxDefaults.colors(checkedColor = Color.Blue)
            )
            Text(
                text = "Acepto los ",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "términos y condiciones",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.minecraft.net/es-es/terms/r2"))
                    navController.context.startActivity(intent)
                }
            )
        }

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                if (nombre.isNotEmpty() && apellidos.isNotEmpty() && correoValido && contrasenaValida && terminosAceptados) {
                    Firebase.auth.createUserWithEmailAndPassword(correo, contrasena)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = task.result?.user?.uid
                                val db = Firebase.firestore
                                val user = hashMapOf(
                                    "nombre" to nombre,
                                    "apellidos" to apellidos,
                                    "correo" to correo,
                                    "tipo" to "general"
                                )
                                userId?.let {
                                    db.collection("usuarios").document(it)
                                        .set(user)
                                        .addOnSuccessListener {
                                            navController.navigate("pantalla5")
                                        }
                                        .addOnFailureListener { e ->
                                            mensajeError = "Error al guardar la cuenta: ${e.message}"
                                        }
                                }
                            } else {
                                mensajeError = "Error al crear la cuenta: ${task.exception?.message}"
                            }
                        }
                } else {
                    mensajeError = "Por favor completa todos los campos correctamente"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Crear Cuenta")
        }

        Spacer(modifier = Modifier.height(36.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "Soy Clínica Penal",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                modifier = Modifier.clickable { navController.navigate("pantalla6") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla4Preview() {
    ClinicaPenalTheme {
        Pantalla4(navController = rememberNavController())
    }
}
