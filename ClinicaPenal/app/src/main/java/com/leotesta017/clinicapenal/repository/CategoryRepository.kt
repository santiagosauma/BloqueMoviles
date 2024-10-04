package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Categoria
import kotlinx.coroutines.tasks.await

class CategoryRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todas las categorías con solo los datos básicos
    suspend fun getCategorias(): List<Categoria> {
        return try {
            val snapshot = firestore.collection("categorias").get().await()
            snapshot.documents.map { document ->
                Categoria(
                    id = document.id,
                    titulo = document.getString("titulo") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    url_imagen = document.getString("url_imagen") ?: "",
                    contenido = ""
                )
            }
        } catch (e: Exception) {
            emptyList()  // Devuelve una lista vacía en caso de error
        }
    }

    suspend fun getContenidoById(categoriaId: String): String {
        return try {
            val document = firestore.collection("categorias").document(categoriaId).get().await()
            document.getString("contenido") ?: ""
        } catch (e: Exception) {
            ""  // Devuelve un string vacío en caso de error
        }
    }


    // Método para agregar un nuevo categoria
    suspend fun addCategoria(categoria: Categoria): Boolean {
        return try {
            firestore.collection("categorias")
                .add(categoria)
                .await()
            true  // Devuelve true si se agregó correctamente
        } catch (e: Exception) {
            false  // Devuelve false en caso de error
        }
    }


    // Método para actualizar una categoria existente basado en su ID
    suspend fun updateCategoria(categoria: Categoria): Boolean {
        return try {
            firestore.collection("categorias")
                .document(categoria.id)  // Se utiliza el ID del servicio para ubicar el documento
                .set(categoria)
                .await()
            true  // Devuelve true si se actualizó correctamente
        } catch (e: Exception) {
            false  // Devuelve false en caso de error
        }
    }
}
