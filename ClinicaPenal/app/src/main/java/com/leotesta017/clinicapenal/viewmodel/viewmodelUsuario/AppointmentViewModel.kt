package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.repository.userRepository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {

    private val repository = AppointmentRepository()

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> = _appointment

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener una cita por su ID
    fun fetchAppointment(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getAppointmentById(id)
                if (result != null) {
                    _appointment.value = result
                } else {
                    _error.value = "Cita no encontrada"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar la cita: ${e.message}"
            }
        }
    }

    // Método para agregar una nueva cita y actualizar el caso
    fun addNewAppointment(appointment: Appointment, caseId: String) {
        viewModelScope.launch {
            val success = repository.addAppointmentToCase(appointment, caseId)
            if (!success) {
                _error.value = "Error al agregar la cita al caso"
            }
        }
    }

    // Método para actualizar una cita existente
    fun updateAppointment(id: String, appointmentData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateAppointment(id, appointmentData)
            if (!success) {
                _error.value = "Error al actualizar la cita"
            }
        }
    }
}
