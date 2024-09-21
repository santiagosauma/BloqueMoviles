package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Categoria
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todas las categorías
    suspend fun getCategories(): List<Categoria> {
        return try {
            val snapshot = firestore.collection("categorias").get().await()
            snapshot.documents.map { document ->
                Categoria(
                    titulo = document.getString("titulo") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    url_imagen = document.getString("url_imagen") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
