package com.leotesta017.clinicapenal.repository.userRepository

import android.util.Log
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
            val document = firestore.collection("cases").document(id).get().await()

            if (document.exists()) {
                val case = document.toObject(Case::class.java)
                case
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAllUnrepresentedCasesWithLastAppointment(appointmentRepository: AppointmentRepository): List<Triple<Case, String, Boolean>> {
        return try {
            val snapshot = firestore.collection("cases").get().await()

            // Lista para almacenar los casos sin representación con el último Appointment
            val unrepresentedCasesList = mutableListOf<Triple<Case, String, Boolean>>()

            // Iteramos por cada caso
            snapshot.documents.forEach { document ->
                val case = document.toObject(Case::class.java)
                case?.let {
                    // Verificar si el caso no está representado
                    if (!case.represented) {
                        // Verificar si el caso tiene citas asociadas
                        if (case.listAppointments.isNotEmpty()) {
                            val lastAppointmentId = case.listAppointments.last()
                            val lastAppointment = appointmentRepository.getAppointmentById(lastAppointmentId)

                            if (lastAppointment != null) {
                                // Convertir la fecha de la cita a String
                                val appointmentDate = lastAppointment.fecha.toDate().toString()
                                // Agregar el caso a la lista junto con la fecha y si está representado
                                unrepresentedCasesList.add(Triple(case, appointmentDate, case.represented))
                            }
                        } else {
                            // Si no tiene citas, agregar el caso con "Sin citas"
                            unrepresentedCasesList.add(Triple(case, "Sin citas", case.represented))
                        }
                    }
                }
            }

            unrepresentedCasesList
        } catch (e: Exception) {
            emptyList() // En caso de error, devolvemos una lista vacía
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

    // Eliminar un caso y todos los elementos asociados (appointments, comments, extraInfo)
    suspend fun deleteCase(caseId: String): Boolean {
        return try {
            // Obtener el caso para acceder a las listas de appointments, comments y extraInfo
            val caseSnapshot = firestore.collection("cases").document(caseId).get().await()

            if (caseSnapshot.exists()) {
                val case = caseSnapshot.toObject(Case::class.java)
                case?.let {
                    // Eliminar todos los appointments asociados
                    it.listAppointments.forEach { appointmentId ->
                        firestore.collection("appointments").document(appointmentId).delete().await()
                    }

                    // Eliminar todos los comentarios asociados
                    it.listComents.forEach { commentId ->
                        firestore.collection("comments").document(commentId).delete().await()
                    }

                    // Eliminar toda la extraInfo asociada
                    it.listExtraInfo.forEach { extraInfoId ->
                        firestore.collection("extraInfo").document(extraInfoId).delete().await()
                    }

                    // Finalmente, eliminar el caso
                    firestore.collection("cases").document(caseId).delete().await()

                    true // Operación exitosa
                } ?: false // Caso no encontrado
            } else {
                false // El caso no existe
            }
        } catch (e: Exception) {
            false // Error al eliminar el caso
        }
    }
}
