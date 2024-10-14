@file:Suppress("LABEL_NAME_CLASH")

package com.leotesta017.clinicapenal.view.loginRegister

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.model.modelUsuario.UserIdData
import com.leotesta017.clinicapenal.view.theme.ClinicaPenalTheme

@Composable
fun Pantalla5(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

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
            text = "Iniciar Sesión",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
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

        Text(
            text = "Olvidé mi contraseña",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://support.google.com/fiber/answer/2676662?hl=es"))
                    navController.context.startActivity(intent)
                }
        )
        Button(
            onClick = {
                if (correo.isNotEmpty() && contrasena.isNotEmpty())
                {
                    auth.signInWithEmailAndPassword(correo, contrasena)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = auth.currentUser?.uid
                                if (uid != null)
                                {
                                    UserIdData.userId = uid
                                    db.collection("usuarios").document(uid).get()

                                        .addOnSuccessListener { document ->
                                            if (document != null && document.exists())
                                            {
                                                val tipoUsuario = document.getString("tipo")

                                                // Obtener el token FCM y guardarlo en la base de datos
                                                FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
                                                    if (!tokenTask.isSuccessful) {
                                                        Log.w("FCM", "Fetching FCM registration token failed", tokenTask.exception)
                                                        return@addOnCompleteListener
                                                    }
                                                    // Obtener el token
                                                    val token = tokenTask.result
                                                    // Guardar el token FCM en Firestore en el documento del usuario
                                                    db.collection("usuarios").document(uid)
                                                        .update("fcmTokens", FieldValue.arrayUnion(token))
                                                        .addOnSuccessListener {
                                                            Log.d("FCM", "FCM token successfully saved in Firestore")
                                                        }
                                                        .addOnFailureListener {
                                                            Log.e("FCM", "Error saving FCM token", it)
                                                        }
                                                }

                                                when (tipoUsuario)
                                                {
                                                    "general" -> navController.navigate("pantallainfocategoriasgeneral")
                                                    "estudiante" -> navController.navigate("pantallainfoestudiante")
                                                    "colaborador" -> navController.navigate("pantallainfoadmin")
                                                    else -> {
                                                        mensajeError = "Error al obtener el tipo de usuario."
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                mensajeError = "Error: Correo inexistente."
                                            }
                                        }
                                        .addOnFailureListener{
                                            mensajeError = "Error al conectarse a la base de datos."
                                        }
                                }
                            }
                            else
                            {
                                when (task.exception?.message)
                                {
                                    "The email address is badly formatted." -> {
                                        mensajeError = "Error: Por favor sea serio."
                                    }
                                    "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                        mensajeError = "Error: Correo inexistente."
                                    }
                                    "The password is invalid or the user does not have a password." -> {
                                        mensajeError = "Error: Contraseña incorrecta."
                                    }
                                    else -> {
                                        mensajeError = "Error: Correo y contraseña incorrectos."
                                    }
                                }
                            }
                        }
                        .addOnFailureListener {
                            mensajeError = "Error: Correo o contraseña incorrectos."
                        }
                }
                else
                {
                    mensajeError = "Por favor complete los campos."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF002366),
                contentColor = Color.White
            )
        ) {
            Text(text = "Iniciar Sesión")
        }

        if (mensajeError.isNotEmpty()) {
            Text(
                text = mensajeError,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Pantalla5Preview() {
    ClinicaPenalTheme {
        Pantalla5(navController = rememberNavController())
    }
}
