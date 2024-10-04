package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Comentario
import kotlinx.coroutines.tasks.await

class ComentarioRepository {

    private val firestore = Firebase.firestore

    // Obtener un comentario por su ID
    suspend fun getComentarioById(id: String): Comentario? {
        return try {
            val document = firestore.collection("comments")
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

    // Agregar un nuevo comentario y actualizar la lista de comentarios del usuario
    suspend fun addComentario(comentario: Comentario, userId: String): Boolean {
        return try {
            val comentarioRef = firestore.collection("comments").document(comentario.comentario_id)

            // Primero, agregar el comentario a la colección "comments"
            comentarioRef.set(comentario).await()

            // Luego, actualizar la lista de comentarios del usuario
            val userRef = firestore.collection("usuarios").document(userId)
            userRef.update("listComments", FieldValue.arrayUnion(comentario.comentario_id)).await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Actualizar un comentario existente
    suspend fun updateComentario(id: String, comentarioData: Map<String, Any>): Boolean {
        return try {
            firestore.collection("comments")
                .document(id)
                .update(comentarioData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
