package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import kotlinx.coroutines.tasks.await

class CaseRepository {

    private val firestore = Firebase.firestore
    private val usuarioRepository = UsuarioRepository()

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

    // Método para asignar abogado o estudiante a un caso
    suspend fun assignUserToCase(caseId: String, userId: String, role: String): Boolean {
        return try {
            val (userName, userType) = usuarioRepository.getUserNameById(userId)

            if (userName != null && userType != null) {
                // Validar si el tipo es correcto
                if ((role == "lawyerAssigned" && userType == "colaborador") ||
                    (role == "studentAssigned" && userType == "estudiante")) {

                    val caseRef = firestore.collection("cases").document(caseId)
                    val updateData = mapOf(role to userId)  // Actualizar abogado o estudiante

                    caseRef.update(updateData).await()
                    true  // Asignación exitosa
                } else {
                    false  // Tipo de usuario incorrecto
                }
            } else {
                false  // Usuario no encontrado
            }
        } catch (e: Exception) {
            false  // Error al asignar el usuario
        }
    }


    // Agregar un nuevo caso
    suspend fun addCase(case: Case, userId: String): Boolean {
        return try {
            val caseRef = firestore.collection("cases").document(case.case_id)

            // Primero, agregar el caso a la colección "cases"
            caseRef.set(case).await()

            // Luego, actualizar la lista de casos del usuario
            val userRef = firestore.collection("usuarios").document(userId)
            userRef.update("listCases", FieldValue.arrayUnion(case.case_id)).await()

            true
        } catch (e: Exception) {
            false
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