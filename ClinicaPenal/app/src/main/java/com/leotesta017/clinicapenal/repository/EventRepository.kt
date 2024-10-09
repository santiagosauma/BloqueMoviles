package com.leotesta017.clinicapenal.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.Evento
import kotlinx.coroutines.tasks.await

class EventRepository {

    private val firestore = Firebase.firestore

    suspend fun getEventos(): List<Evento> {
        return try {
            val snapshot = firestore.collection("eventos").get().await()
            snapshot.documents.map { document ->
                val timestamp = document.getTimestamp("fecha") ?: Timestamp.now()
                val fecha = timestamp.toDate()
                Evento(
                    fecha = fecha,
                    titulo = document.getString("titulo") ?: "",
                    lugar = document.getString("lugar") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
