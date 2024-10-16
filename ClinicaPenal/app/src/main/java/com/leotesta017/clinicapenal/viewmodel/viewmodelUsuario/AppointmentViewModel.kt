package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.notificaciones.NotificationServiceSingleton
import com.leotesta017.clinicapenal.repository.userRepository.AppointmentRepository
import com.leotesta017.clinicapenal.repository.userRepository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppointmentViewModel : ViewModel() {

    val repository = AppointmentRepository()
    val userRepository = UsuarioRepository()

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


    fun createAppointmentAndNewCase(
        appointment: Appointment,
        userId: String,
        place: String,
        lugarProcedencia: String,
        victima: Boolean,
        investigado: Boolean,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                // Llamar a la función del repositorio para crear la cita y el nuevo caso
                val result = repository.addAppointmentAndCreateNewCase(
                    appointment,
                    userId,
                    place,
                    lugarProcedencia,
                    victima,
                    investigado,

                )

                // Actualizar el estado del resultado
                _appointmentResult.value = result
                val notificationService = NotificationServiceSingleton.getInstance(context = context)

                notificationService.sendNotificationToAllAbogadosYEstudiantes(
                        title = "Nuevo caso creado",
                        message = "Se ha registrado una nueva cita y creado un caso a partir de ella.\nRevisa los detalles en la aplicación."
                )



                // Si la operación no fue exitosa, mostrar mensaje de error
                if (!result) {
                    _error.value = "No se pudo crear el caso o agendar la cita."
                }

            } catch (e: Exception) {
                // Si hay una excepción, manejar el error
                _error.value = e.message
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

    // Función para reiniciar los estados
    fun resetAppointmentResult() {
        _appointmentResult.value = false
        _error.value = null
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

    // Función para verificar si el usuario puede crear un nuevo caso
    fun canCreateNewCase(userId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val lastAvailableCase = userRepository.getLastAvailableCase(userId)
            // Si no existe un último caso disponible, puede crear un nuevo caso
            onResult(lastAvailableCase == null)
        }
    }

}
