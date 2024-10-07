package com.leotesta017.clinicapenal.repository.userRepository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import kotlinx.coroutines.tasks.await

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


    // Método para agregar una nueva cita y actualizar el caso (en lugar de usuario)
    suspend fun addAppointmentToCase(appointment: Appointment, caseId: String): Boolean {
        return try {
            val appointmentRef = firestore.collection("appointments")
                .document(appointment.appointment_id)

            // Primero, agregar la cita
            appointmentRef.set(appointment).await()

            // Ahora, actualizar la lista de citas del caso
            val caseRef = firestore.collection("cases").document(caseId)
            caseRef.update("listAppointments", FieldValue.arrayUnion(appointment.appointment_id))
                .await()

            true
        } catch (e: Exception) {
            false
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
}

