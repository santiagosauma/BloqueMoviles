package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.repository.userRepository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {

    val repository = AppointmentRepository()

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> = _appointment

    private val _appointmentResult = MutableStateFlow<Boolean>(false)
    val appointmentResult: StateFlow<Boolean> = _appointmentResult

    // Estado para almacenar las citas filtradas por fecha
    private val _appointmentsByDate = MutableStateFlow<List<Appointment>>(emptyList())
    val appointmentsByDate: StateFlow<List<Appointment>> = _appointmentsByDate

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

    // Función para obtener citas por fecha
    fun fetchAppointmentsByDate(date: String) {
        viewModelScope.launch {
            try {
                val result = repository.getAppointmentsByDate(date)
                if (result.isNotEmpty()) {
                    _appointmentsByDate.value = result
                    _error.value = ""
                } else {
                    _error.value = "No hay citas para la fecha seleccionada"
                    _appointmentsByDate.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener las citas: ${e.message}"
            }
        }
    }


    // Método para agregar una nueva cita y crear un nuevo caso
    fun addAppointmentAndCreateNewCase(appointment: Appointment, userId: String, place: String) {
        viewModelScope.launch {
            try {
                // Llamamos al repositorio para ejecutar la función
                val success = repository.addAppointmentAndCreateNewCase(appointment, userId, place)
                if (success) {
                    _appointmentResult.value = true
                } else {
                    _error.value = "Error al crear un nuevo caso y agregar la cita."
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    // Método para agregar una cita a un caso existente
    fun addAppointmentToExistingCase(appointment: Appointment, caseId: String) {
        viewModelScope.launch {
            try {
                // Llamamos al repositorio para ejecutar la función
                val success = repository.addAppointmentToExistingCase(appointment, caseId)
                if (success) {
                    _appointmentResult.value = true
                } else {
                    _error.value = "Error al agregar la cita al caso existente."
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    fun resetAppointments() {
        _appointmentsByDate.value = emptyList()  // Vaciar las citas almacenadas
        _error.value = ""
    }

    // Método para resetear el estado de error y resultado
    fun resetState() {
        _appointmentResult.value = false
        _error.value = ""
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
