package com.leotesta017.clinicapenal.repository.userRepository

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
            val document = firestore.collection("appointments")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(Appointment::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Método para agregar una nueva cita y actualizar el usuario
    suspend fun addAppointment(appointment: Appointment, userId: String): Boolean {
        return try {
            val appointmentRef = firestore.collection("appointments")
                .document(appointment.appointment_id)

            // Primero, agregar la cita
            appointmentRef.set(appointment).await()

            // Ahora, actualizar la lista de citas del usuario
            val userRef = firestore.collection("usuarios").document(userId)
            userRef.update("listAppointments", FieldValue.arrayUnion(appointment.appointment_id))
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Método para actualizar una cita existente
    suspend fun updateAppointment(id: String, appointment: Map<String, Any>): Boolean {
        return try {
            firestore.collection("appointments")
                .document(id)
                .update(appointment)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
