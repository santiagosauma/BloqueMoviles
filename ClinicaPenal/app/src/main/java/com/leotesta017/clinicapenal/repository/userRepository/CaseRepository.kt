package com.leotesta017.clinicapenal.repository.userRepository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Comment

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

    // Actualizar un caso para marcarlo como suspendido y remover de la lista de casos de los usuarios asignados
    suspend fun updateCaseToDiscard(caseId: String): Boolean {
        return try {
            // Obtener el caso
            val caseSnapshot = firestore.collection("cases").document(caseId).get().await()

            if (caseSnapshot.exists()) {
                val case = caseSnapshot.toObject(Case::class.java)
                case?.let {
                    // Verificar si hay abogado y estudiante asignados para eliminarlos de sus listas de casos
                    val lawyerId = it.lawyerAssigned
                    val studentId = it.studentAssigned

                    // Crear un mapa de los campos a actualizar en el caso
                    val updates = mapOf(
                        "lawyerAssigned" to "",      // Desasignar el abogado
                        "studentAssigned" to "",     // Desasignar el estudiante
                        "suspended" to true,         // Marcar el caso como suspendido
                        "state" to "Suspendido",     // Cambiar el estado a 'Suspendido'
                        "situation" to "Caso descartado", // Cambiar la situación a 'Caso descartado'
                        "represented" to false,      // Marcar el caso como no representado
                        "available" to false,        // Marcar como no disponible
                        "completed" to true          // Marcar el caso como completado
                    )

                    // Actualizar el caso con los nuevos valores
                    firestore.collection("cases").document(caseId).update(updates).await()

                    // Eliminar el caso de la lista de casos del abogado asignado
                    if (lawyerId.isNotEmpty()) {
                        firestore.collection("usuarios").document(lawyerId)
                            .update("listCases", FieldValue.arrayRemove(caseId)).await()
                    }

                    // Eliminar el caso de la lista de casos del estudiante asignado
                    if (studentId.isNotEmpty()) {
                        firestore.collection("usuarios").document(studentId)
                            .update("listCases", FieldValue.arrayRemove(caseId)).await()
                    }

                    val usersQuerySnapshot = firestore.collection("usuarios")
                        .whereEqualTo("tipo", "general")
                        .get()
                        .await()

                    // Paso 2: Iterar sobre cada usuario para verificar su listCases
                    for (userDocument in usersQuerySnapshot.documents) {
                        val userId = userDocument.id
                        val userCases = userDocument.get("listCases") as? List<String> ?: emptyList()

                        // Paso 3: Verificar si el ID del caso está en la lista
                        if (caseId in userCases) {
                            // Paso 4: Eliminar el ID del caso de la lista
                            firestore.collection("usuarios")
                                .document(userId)
                                .update("listCases", FieldValue.arrayRemove(caseId))
                                .await()
                        }
                    }

                    true // Operación exitosa
                } ?: false // Caso no encontrado
            } else {
                false // El caso no existe
            }
        } catch (e: Exception) {
            false // Error al actualizar el caso
        }
    }


    suspend fun getCaseWithDetails(caseId: String): Pair<Case, Triple<List<Appointment>, List<Comment>, List<ExtraInfo>>>? {
        return try {
            // Obtener el caso
            val caseSnapshot = firestore.collection("cases").document(caseId).get().await()

            if (caseSnapshot.exists()) {
                val case = caseSnapshot.toObject(Case::class.java)

                // Verificar que el caso no sea nulo
                case?.let {
                    // Obtener las listas de IDs de citas, comentarios y extraInfo
                    val appointmentIds = it.listAppointments
                    val comentIds = it.listComents
                    val extraInfoIds = it.listExtraInfo

                    // Obtener las citas (Appointments) a partir de sus IDs
                    val appointments = appointmentIds.mapNotNull { appointmentId ->
                        firestore.collection("appointments").document(appointmentId).get().await().toObject(Appointment::class.java)
                    }

                    // Obtener los comentarios (Comments) a partir de sus IDs
                    val comments = comentIds.mapNotNull { commentId ->
                        firestore.collection("coments").document(commentId).get().await().toObject(Comment::class.java)
                    }

                    // Obtener la información extra (ExtraInfo) a partir de sus IDs
                    val extraInfoList = extraInfoIds.mapNotNull { extraInfoId ->
                        firestore.collection("extrainfo").document(extraInfoId).get().await().toObject(ExtraInfo::class.java)
                    }

                    Pair(case, Triple(appointments, comments, extraInfoList))
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }




}
