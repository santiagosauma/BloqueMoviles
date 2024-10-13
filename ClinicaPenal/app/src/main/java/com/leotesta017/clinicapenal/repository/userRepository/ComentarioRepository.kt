package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Comentario
import kotlinx.coroutines.tasks.await

class ComentarioRepository {

    private val firestore = Firebase.firestore

    private val usuarioRepository = UsuarioRepository()
    // Obtener un comentario por su ID
    suspend fun getComentarioById(id: String): Comentario? {
        return try {
            val document = firestore.collection("coments")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Comentario::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }





    // Método para generar y agregar un nuevo comentario
    suspend fun addNewComentarioToCase(contenido: String, important: Boolean, userId: String, caseId: String): Boolean {
        return try {
            // Generar un nuevo ID para el comentario en Firestore
            val newComentarioId = firestore.collection("coments").document().id
                // Crear un nuevo comentario
            val nuevoComentario = Comentario(
                comentario_id = newComentarioId,
                contenido = contenido,
                fecha = Timestamp.now(),
                madeBy = userId,  // El userId proporcionado
                important = important,
                representation = ""  // Dejar vacío por defecto
            )
            // Guardar el comentario en la colección "coments"
            val comentarioRef = firestore.collection("coments").document(newComentarioId)
            comentarioRef.set(nuevoComentario).await()

            // Actualizar la lista de comentarios del caso
            val caseRef = firestore.collection("cases").document(caseId)
            caseRef.update("listComents", FieldValue.arrayUnion(newComentarioId)).await()

            true  // Operación exitosa
        }
        catch (e: Exception) {
                false  // Error durante el proceso //
        }
    }



    // Actualizar un comentario existente
    suspend fun updateComentario(id: String, comentarioData: Map<String, Any>): Boolean {
        return try {
            firestore.collection("coments")
                .document(id)
                .update(comentarioData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserNameByComentarioId(comentarioId: String): String? {
        return try {
            val comentario = getComentarioById(comentarioId)

            comentario?.madeBy?.let { userId ->
                // Obtener el nombre completo del usuario
                usuarioRepository.getUserNameFromComent(userId)
            }
        } catch (e: Exception) {
            null
        }
    }

}

