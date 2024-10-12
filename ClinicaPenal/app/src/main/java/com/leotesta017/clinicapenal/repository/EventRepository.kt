package com.leotesta017.clinicapenal.repository

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
                val timestamp = document.getTimestamp("fecha") ?: com.google.firebase.Timestamp.now()
                val fecha = timestamp.toDate()
                Evento(
                    id = document.id, // Asignar el ID del documento
                    fecha = fecha,
                    titulo = document.getString("titulo") ?: "",
                    lugar = document.getString("lugar") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun addEvento(evento: Evento, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val eventoData = hashMapOf(
            "fecha" to evento.fecha,
            "titulo" to evento.titulo,
            "lugar" to evento.lugar
        )

        firestore.collection("eventos")
            .add(eventoData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception.message ?: "Error desconocido") }
    }

    suspend fun deleteEvento(id: String) {
        firestore.collection("eventos").document(id).delete().await()
    }
}
