package com.leotesta017.clinicapenal.repository.userRepository


import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class Case_CounterRepository {
    private val firestore = Firebase.firestore
    private val caseCounterRef = firestore.collection("case_counter").document("OqwKECQFxrFz9bCl6ZIi") // El documento donde guardas los IDs

    // Esta función solo retorna el índice, sin usar StateFlow
    suspend fun findOrAddCase(caseId: String): Int {
        return try {
            val documentSnapshot = caseCounterRef.get().await()

            // Obtener la lista de IDs de casos
            val caseIds: MutableList<String> = documentSnapshot.get("case_id") as? MutableList<String> ?: mutableListOf()

            // Buscar la posición del caso en la lista
            val index = caseIds.indexOf(caseId)

            if (index == -1) {
                // Si el caso no está en la lista, lo agregamos
                caseIds.add(caseId)
                caseCounterRef.update("case_id", caseIds).await()

                // Devolvemos la nueva posición (el índice del último elemento agregado)
                caseIds.size // Retornamos el tamaño de la lista (que será la nueva posición)
            } else {
                // Si el caso ya está en la lista, devolvemos su posición (+1 para que comience en 1 en vez de 0)
                index + 1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1 // Retornamos -1 en caso de error
        }
    }
}
