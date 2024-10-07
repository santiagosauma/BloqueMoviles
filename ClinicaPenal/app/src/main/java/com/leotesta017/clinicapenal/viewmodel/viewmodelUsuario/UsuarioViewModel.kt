package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import com.leotesta017.clinicapenal.repository.userRepository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel()
{

    private val repository = UsuarioRepository()
    private val caseViewModel = CaseViewModel()
    private val appointmentViewModel = AppointmentViewModel()

    private val _usuario = MutableStateFlow<Usuario>(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _userCasesWithAppointments = MutableStateFlow<List<Pair<Case, List<Appointment>>>>(emptyList())  // Cambiamos a lista de Case
    val userCasesWithAppointments: StateFlow<List<Pair<Case, List<Appointment>>>> = _userCasesWithAppointments

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userType = MutableStateFlow<String?>(null)
    val userType: StateFlow<String?> = _userType

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener información del usuario
    fun fetchUsuario(id: String)
    {
        viewModelScope.launch {
            try
            {
                val currentUsuario = repository.getUserInfo(id)
                _usuario.value = currentUsuario
            }
            catch (e: Exception)
            {
                _error.value = "Error al cargar datos del usuario: ${e.message}"
            }
        }
    }

    // Crear un nuevo usuario en Firestore
    fun createUsuario(id: String, nombre: String, apellidos: String,
                      correo: String, tipo: String)
    {
        viewModelScope.launch{
            val success = repository.createUser(id, nombre, apellidos, correo, tipo)
            if (success)
            {
                _userId.value = id
            }
            else
            {
                _error.value = "Error al crear el usuario"
            }
        }
    }

    fun fetchUserNameAndType(id: String)
    {
        viewModelScope.launch {
            try
            {
                val (name, type) = repository.getUserNameById(id)
                if (name != null && type != null)
                {
                    _userName.value = name
                    _userType.value = type
                }
                else
                {
                    _error.value = "Usuario no encontrado"
                }
            }
            catch (e: Exception)
            {
                _error.value = "Error al obtener los datos del usuario: ${e.message}"
            }
        }
    }

    fun fetchUserType(id: String)
    {
        viewModelScope.launch {
            try
            {
                val type = repository.getUserTypeById(id)  // Solo obtener el tipo de usuario
                _userType.value = type ?: "Tipo de usuario no encontrado"
            }
            catch (e: Exception)
            {
                _error.value = "Error al obtener el tipo del usuario: ${e.message}"
            }
        }
    }

    fun fetchUserCasesWithDetailsAndAppointments(userId: String) {
        viewModelScope.launch {
            try {
                // Obtener los IDs de casos del usuario
                val caseIds = repository.getUserCasesById(userId)

                // Crear una lista para almacenar los pares de Caso y Citas
                val caseWithAppointmentsList = mutableListOf<Pair<Case, List<Appointment>>>()

                // Iterar sobre los IDs de casos y obtener la información completa de cada caso
                caseIds.forEach { caseId ->
                    val case = caseViewModel.repository.getCaseById(caseId) // Obtener caso por ID
                    if (case != null) {

                        // Aquí traemos las citas asociadas al caso
                        val appointmentIds = case.listAppointments

                        // Crear una lista para almacenar las citas del caso actual
                        val appointments = mutableListOf<Appointment>()

                        // Obtener información de cada cita asociada al caso
                        appointmentIds.forEach { appointmentId ->
                            val appointment = appointmentViewModel.repository.getAppointmentById(appointmentId) // Obtener cita por ID
                            if (appointment != null) {
                                appointments.add(appointment)
                            } else {
                            }
                        }
                        // Agregar el par de Caso con su lista de citas a la lista de pares
                        caseWithAppointmentsList.add(Pair(case, appointments))
                    } else {
                        _error.value = "Error al obtener información del caso con ID: $caseId"
                    }
                }
                // Actualizar la lista de pares de casos completos con citas en el MutableStateFlow
                _userCasesWithAppointments.value = caseWithAppointmentsList
            } catch (e: Exception) {
                _error.value = "Error al obtener los casos completos: ${e.message}"
            }
        }
    }

}
