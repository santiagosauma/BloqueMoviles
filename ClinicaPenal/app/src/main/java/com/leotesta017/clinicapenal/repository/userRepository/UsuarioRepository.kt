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
                Usuario()
            }
        } catch (e: Exception) {
            Usuario()
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
                Pair(null, null)
            }
        } catch (e: Exception) {
            Pair(null, null)
        }
    }

    suspend fun getUserNameFromComent(userId: String): String? {
        return try {
            val document = firestore.collection("usuarios")
                .document(userId)
                .get()
                .await()

            if (document.exists()) {
                val nombre = document.getString("nombre")
                val apellidos = document.getString("apellidos")
                if (nombre != null && apellidos != null) {
                    "$nombre $apellidos"
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
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
                document.getString("tipo") ?: ""
            }
            else
            {
                ""
            }
        }
        catch (e: Exception)
        {
            ""
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
                document.get("listCases") as? List<String> ?:  emptyList<String>()
            }
            else
            {
                emptyList<String>()
            }
        }
        catch (e: Exception)
        {
            emptyList<String>()
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

    suspend fun getUserByCaseId(caseId: String,type:String): Usuario? {
        val querySnapshot = firestore.collection("usuarios")
            .whereEqualTo("tipo", type)
            .whereArrayContains("listCases", caseId)
            .get()
            .await()

        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents.first().toObject(Usuario::class.java)
        } else {
            null
        }
    }

    suspend fun getCollaboratorsAndStudents(): List<Usuario> {
        return try {
            val querySnapshot = firestore.collection("usuarios")
                .whereNotEqualTo("tipo", "general")
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                document.toObject(Usuario::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }


}
