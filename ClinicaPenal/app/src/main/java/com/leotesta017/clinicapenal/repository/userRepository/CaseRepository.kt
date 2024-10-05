package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import kotlinx.coroutines.tasks.await

class CaseRepository {

    private val firestore = Firebase.firestore

    // Obtener un caso por su ID
    suspend fun getCaseById(id: String): Case? {
        return try {
            val document = firestore.collection("cases")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Case::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Agregar un nuevo caso y asignar el ID al usuario
    suspend fun addCase(case: Case, userId: String): Boolean {
        return try {
            val caseRef = firestore.collection("cases").document(case.case_id)

            // Primero, agregar el caso
            caseRef.set(case).await()

            // Luego, actualizar la lista de casos del usuario
            val userRef = firestore.collection("usuarios").document(userId)
            userRef.update("listCases", FieldValue.arrayUnion(case.case_id)).await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Método para asignar abogado o estudiante a un caso
    suspend fun assignUserToCase(caseId: String, userId: String, role: String): Boolean {
        return try {
            // Referencia al documento del caso en Firestore
            val caseRef = firestore.collection("cases").document(caseId)

            // Dependiendo del rol, actualiza el campo correspondiente
            val updateData = when (role) {
                "lawyerAssigned" -> mapOf("lawyerAssigned" to userId)  // Asignar abogado
                "studentAssigned" -> mapOf("studentAssigned" to userId)  // Asignar estudiante
                else -> return false  // Si el rol no es válido, retornamos false
            }

            // Actualizar el documento del caso con el campo correspondiente
            caseRef.update(updateData).await()

            true  // Asignación exitosa
        } catch (e: Exception) {
            false  // Ocurrió un error durante la asignación
        }
    }

    // Actualizar un caso existente
    suspend fun updateCase(id: String, caseData: Map<String, Any>): Boolean {
        return try {
            firestore.collection("cases")
                .document(id)
                .update(caseData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
