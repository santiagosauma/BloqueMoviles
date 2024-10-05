package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import kotlinx.coroutines.tasks.await

@Suppress("UNCHECKED_CAST")
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

    suspend fun getUserTypeById(id: String): String
    {
        return try
        {
            val document = firestore.collection("usuarios")
                .document(id)
                .get()
                .await()

            if (document.exists())
            {
                document.getString("tipo") ?: ""  // Obtener el nombre directamente del documento
            }
            else
            {
                ""  // Retorna una cadena vacía si el documento no existe
            }
        }
        catch (e: Exception)
        {
            ""  // Retorna una cadena vacía en caso de error
        }
    }

    suspend fun getUserCasesById(id: String): List<String>
    {
        return try
        {
            val document = firestore.collection("usuarios")
                .document(id)
                .get()
                .await()

            if (document.exists())
            {
                document.get("listCases") as? List<String> ?:  emptyList<String>()  // Obtener el nombre directamente del documento
            }
            else
            {
                emptyList<String>()  // Retorna una cadena vacía si el documento no existe
            }
        }
        catch (e: Exception)
        {
            emptyList<String>()  // Retorna una cadena vacía en caso de error
        }
    }


    // Método para crear un nuevo usuario (PUT) en Firestore
    suspend fun createUser(id: String, nombre: String, apellidos: String,
                           correo: String, tipo: String): Boolean
    {
        return try {
            // Crear el objeto Usuario con los parámetros proporcionados
            val user = Usuario(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                correo = correo,
                tipo = tipo
            )

            // Guardar el objeto Usuario en Firestore
            firestore.collection("usuarios")
                .document(id)
                .set(user)
                .await()

            true
        }
        catch (e: Exception)
        {
            false
        }
    }
}
