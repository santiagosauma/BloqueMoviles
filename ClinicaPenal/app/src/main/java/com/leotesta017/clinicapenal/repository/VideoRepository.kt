package com.leotesta017.clinicapenal.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Video
import kotlinx.coroutines.tasks.await

class VideoRepository {

    private val firestore = Firebase.firestore

    // Método para obtener todos los videos desde Firestore
    suspend fun getVideos(): List<Video> {
        return try {
            val snapshot = firestore.collection("videos").get().await()
            snapshot.documents.map { document ->
                Video(
                    url_video = document.getString("url_video") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    titulo = document.getString("titulo") ?: "",
                    tipo = document.getString("tipo") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()  // Devuelve una lista vacía en caso de error
        }
    }
}
