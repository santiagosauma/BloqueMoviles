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
                    id = document.id,
                    url_video = document.getString("url_video") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    titulo = document.getString("titulo") ?: "",
                    tipo = document.getString("tipo") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Método para agregar un nuevo video a Firestore
    suspend fun addVideo(descripcion: String, tipo: String, titulo: String, urlVideo: String): Boolean {
        return try {
            val videoData = hashMapOf(
                "descripcion" to descripcion,
                "tipo" to tipo,
                "titulo" to titulo,
                "url_video" to urlVideo
            )
            firestore.collection("videos").add(videoData).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Método para actualizar un video existente en Firestore
    suspend fun updateVideo(videoId: String, video: Video): Boolean {
        return try {
            val videoData = hashMapOf(
                "descripcion" to video.descripcion,
                "tipo" to video.tipo,
                "titulo" to video.titulo,
                "url_video" to video.url_video
            )
            firestore.collection("videos").document(videoId).set(videoData).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Método para obtener un video específico por su ID
    suspend fun getVideoById(videoId: String): Video? {
        return try {
            val document = firestore.collection("videos").document(videoId).get().await()
            if (document.exists()) {
                Video(
                    id = document.id,
                    url_video = document.getString("url_video") ?: "",
                    descripcion = document.getString("descripcion") ?: "",
                    titulo = document.getString("titulo") ?: "",
                    tipo = document.getString("tipo") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Método para eliminar un video específico por su ID
    suspend fun deleteVideoById(videoId: String): Boolean {
        return try {
            firestore.collection("videos").document(videoId).delete().await()
            true // Eliminación exitosa
        } catch (e: Exception) {
            false // En caso de fallo
        }
    }
}
