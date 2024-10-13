package com.leotesta017.clinicapenal.repository.userRepository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class AppointmentRepository {

    private val firestore = Firebase.firestore

    // Método para obtener una cita por su ID
    suspend fun getAppointmentById(id: String): Appointment? {
        return try {
            // Intentar obtener el documento de Firestore
            val document = firestore.collection("appointments")
                .document(id)
                .get()
                .await()

            // Verificar si el documento existe
            if (document.exists()) {
                val appointment = document.toObject(Appointment::class.java)
                Log.d("DebugAppointment", "Cita obtenida: ${appointment?.appointment_id}, Fecha: ${appointment?.fecha?.toDate()}, Confirmada: ${appointment?.confirmed}")
                appointment
            } else {
                Log.e("DebugAppointment", "El documento no existe: $id")
                null
            }
        } catch (e: Exception) {
            Log.e("DebugAppointment", "Error obteniendo cita con ID $id: ${e.message}")
            null
        }
    }

    suspend fun getAppointmentsByDate(date: String): List<Appointment> {
        return try {
            // Obtener todas las citas
            val snapshot = firestore.collection("appointments").get().await()

            // Filtrar citas cuya fecha coincida con la fecha seleccionada
            snapshot.documents.mapNotNull { document ->
                val appointment = document.toObject(Appointment::class.java)
                appointment?.let {
                    // Convertir el `Timestamp` de la cita a un string en el formato de "dd/MM/yyyy"
                    val appointmentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.fecha.toDate())

                    // Comparar con la fecha seleccionada
                    if (appointmentDate == date) {
                        appointment
                    } else {
                        null
                    }
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Método para actualizar una cita existente
    suspend fun updateAppointment(id: String, appointmentData: Map<String, Any>): Boolean {
        return try {
            firestore.collection("appointments")
                .document(id)
                .update(appointmentData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addAppointmentAndCreateNewCase(
        appointment: Appointment,
        userId: String,
        place: String,
        lugarProcedencia: String,
        victima: Boolean,
        investigado: Boolean
    ): Boolean {
        return try {
            // Generar un nuevo ID para el caso
            val newCaseId = firestore.collection("cases").document().id

            // Generar un nuevo ID para la cita
            val newAppointmentId = firestore.collection("appointments").document().id

            // Actualizar el appointment con el nuevo ID generado
            val newAppointment = appointment.copy(appointment_id = newAppointmentId)

            // Generar un nuevo ID para el extraInfo
            val newExtraInfoId = firestore.collection("extrainfo").document().id

            // Crear un nuevo ExtraInfo con los campos inicializados
            val newExtraInfo = ExtraInfo(
                extraInfo_id = newExtraInfoId,
                victima =  victima,
                investigado = investigado,
                lugarProcedencia = lugarProcedencia,
                id_Usuario = userId,

                // Inicializamos los campos restantes como vacíos
                fiscalia = "",
                crime = "",
                ine = "",
                nuc = "",
                carpetaJudicial = "",
                carpetaInvestigacion = "",
                afv = "",
                passwordFV = "",
                fiscalTitular = "",
                unidadInvestigacion = "",
                direccionUI = ""
            )

            // Crear el nuevo caso
            val newCase = Case(
                case_id = newCaseId,
                represented = false,
                available = true,
                completed = false,
                suspended = false,
                lawyerAssigned = "",
                studentAssigned = "",
                place = place,
                situation = "Primer cita creada",
                state = "Activo",
                segundoFormulario = false,
                listAppointments = listOf(newAppointmentId),
                listComents = emptyList(),
                listExtraInfo = listOf(newExtraInfoId)
            )

            // Guardar el nuevo ExtraInfo en la colección "extrainfo"
            firestore.collection("extrainfo").document(newExtraInfoId).set(newExtraInfo).await()

            // Guardar el nuevo caso en la colección "cases"
            firestore.collection("cases").document(newCaseId).set(newCase).await()

            // Guardar la cita con su ID en la colección de "appointments"
            firestore.collection("appointments").document(newAppointmentId).set(newAppointment).await()

            // Actualizar la lista de casos del usuario
            firestore.collection("usuarios").document(userId)
                .update("listCases", FieldValue.arrayUnion(newCaseId)).await()

            true  // Operación exitosa
        } catch (e: Exception) {
            Log.e("Error", "Error creando nuevo caso y agregando cita: ${e.message}")
            false  // Operación fallida
        }
    }



    suspend fun addAppointmentToExistingCase(appointment: Appointment, caseId: String): Boolean {
        return try {
            // Generar un nuevo ID para la cita
            val newAppointmentId = firestore.collection("appointments").document().id

            // Actualizar el appointment con el nuevo ID generado
            val newAppointment = appointment.copy(appointment_id = newAppointmentId)

            // Agregar la cita a la colección "appointments" con el nuevo ID
            firestore.collection("appointments").document(newAppointmentId)
                .set(newAppointment).await()

            // Actualizar la lista de citas del caso existente
            firestore.collection("cases").document(caseId)
                .update("listAppointments", FieldValue.arrayUnion(newAppointmentId))
                .await()

            true  // Operación exitosa
        } catch (e: Exception) {
            Log.e("Error", "Error agregando cita a caso existente: ${e.message}")
            false  // Operación fallida
        }
    }


}

