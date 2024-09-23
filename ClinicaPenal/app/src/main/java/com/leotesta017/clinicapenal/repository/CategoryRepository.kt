package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Categoria
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todas las categorías con solo los datos básicos
    suspend fun getCategoriasBasicas(): List<Categoria> {
        return try {
            val snapshot = firestore.collection("categorias").get().await()
            snapshot.documents.map { document ->
                Categoria(
                    id = document.id,
                    titulo = document.getString("titulo") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    url_imagen = document.getString("url_imagen") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()  // Devuelve una lista vacía en caso de error
        }
    }
}
