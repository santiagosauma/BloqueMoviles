package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.leotesta017.clinicapenal.model.Ejemplo
import com.leotesta017.clinicapenal.model.Recursos
import kotlinx.coroutines.tasks.await

suspend fun getRecursosYejemplos(
    collectionName: String,
    documentId: String,
    firestore: FirebaseFirestore // Si estás usando inyección de dependencias, puedes pasar el Firestore como parámetro
): Pair<List<Recursos>, List<Ejemplo>>
{
    return try {
        // Obtener los ejemplos asociados
        val ejemplosSnapshot = firestore.collection("ejemplos")
            .whereEqualTo("asociacionId", documentId)
            .get()
            .await()
        val ejemplos = ejemplosSnapshot.documents.map { ejemploDoc ->
            Ejemplo(
                id = ejemploDoc.id,
                asociacionId = documentId,
                descripcion = ejemploDoc.getString("descripcion") ?: ""
            )
        }

        // Obtener los recursos asociados
        val recursosSnapshot = firestore.collection("recursos")
            .whereEqualTo("asociacionId", documentId)
            .get()
            .await()
        val recursos = recursosSnapshot.documents.map { recursoDoc ->
            Recursos(
                id = recursoDoc.id,
                asociacionId = documentId,
                url_linkrecurso = recursoDoc.getString("url_linkrecurso") ?: ""
            )
        }

        Pair(recursos, ejemplos)
    } catch (e: Exception) {
        Pair(emptyList(), emptyList())  // Devuelve listas vacías en caso de error
    }
}