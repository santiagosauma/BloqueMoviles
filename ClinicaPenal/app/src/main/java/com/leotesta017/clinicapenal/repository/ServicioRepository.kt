package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Servicio
import kotlinx.coroutines.tasks.await

class ServicioRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todos los servicios con solo los datos básicos
    suspend fun getServicios(): List<Servicio> {
        return try {
            val snapshot = firestore.collection("servicios").get().await()
            snapshot.documents.map { document ->
                Servicio(
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

    // Método para obtener el contenido de un servicio por su ID
    suspend fun getContenidoById(servicioId: String): String {
        return try {
            val document = firestore.collection("servicios").document(servicioId).get().await()
            document.getString("contenido") ?: ""
        } catch (e: Exception) {
            ""  // Devuelve un string vacío en caso de error
        }
    }


    // Método para agregar un nuevo servicio
    suspend fun addServicio(servicio: Servicio): Boolean {
        return try {
            firestore.collection("servicios")
                .add(servicio)
                .await()
            true  // Devuelve true si se agregó correctamente
        } catch (e: Exception) {
            false  // Devuelve false en caso de error
        }
    }


    // Método para actualizar un servicio existente basado en su ID
    suspend fun updateServicio(servicio: Servicio): Boolean {
        return try {
            firestore.collection("servicios")
                .document(servicio.id)  // Se utiliza el ID del servicio para ubicar el documento
                .set(servicio)
                .await()
            true  // Devuelve true si se actualizó correctamente
        } catch (e: Exception) {
            false  // Devuelve false en caso de error
        }
    }
}


