package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Servicio
import kotlinx.coroutines.tasks.await

class ServicioRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todos los servicios
    suspend fun getServicios(): List<Servicio> {
        return try {
            val snapshot = firestore.collection("servicios").get().await()
            snapshot.documents.map { document ->
                Servicio(
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