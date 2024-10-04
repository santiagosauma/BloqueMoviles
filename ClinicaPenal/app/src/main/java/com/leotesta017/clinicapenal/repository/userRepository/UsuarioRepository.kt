package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import kotlinx.coroutines.tasks.await

class UsuarioRepository {

    private val firestore = Firebase.firestore

    // Método para obtener información del usuario por ID
    suspend fun getUserInfo(id: String): Usuario {
        return try {
            val document = firestore.collection("usuarios")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Usuario::class.java) ?: Usuario()
            } else {
                Usuario()  // Usuario vacío si no existe el documento
            }
        } catch (e: Exception) {
            Usuario()  // Usuario vacío en caso de error
        }
    }

    // Obtener información básica de un usuario por ID
    suspend fun getUserNameById(id: String): Pair<String?, String?> {
        return try {
            val document = firestore.collection("usuarios")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                val user = document.toObject(Usuario::class.java)
                // Retornamos el nombre y tipo del usuario
                Pair(user?.nombre, user?.tipo)
            } else {
                Pair(null, null)  // Usuario no encontrado
            }
        } catch (e: Exception) {
            Pair(null, null)  // Error al obtener datos
        }
    }


    // Método para crear un nuevo usuario (PUT) en Firestore
    suspend fun createUser(id: String, nombre: String, apellidos: String, correo: String, tipo: String): Boolean {
        return try {
            // Crear el objeto Usuario con los parámetros proporcionados
            val user = Usuario(id = id, nombre = nombre, apellidos = apellidos, correo = correo, tipo = tipo)

            // Guardar el objeto Usuario en Firestore
            firestore.collection("usuarios")
                .document(id)
                .set(user)
                .await()  // Esperar a que la operación de Firestore se complete

            true  // Se crea el usuario con éxito
        } catch (e: Exception) {
            false  // Error al crear el usuario
        }
    }
}
