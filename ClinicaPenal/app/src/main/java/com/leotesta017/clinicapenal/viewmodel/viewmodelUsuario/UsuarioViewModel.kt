package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import com.leotesta017.clinicapenal.repository.userRepository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel()
{

    val repository = UsuarioRepository()
    private val caseViewModel = CaseViewModel()
    private val appointmentViewModel = AppointmentViewModel()

    private val _usuario = MutableStateFlow<Usuario>(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _userCasesWithAppointments = MutableStateFlow<List<Triple<Case,String,Boolean>>>(emptyList())  // Cambiamos a lista de Case
    val userCasesWithAppointments: StateFlow<List<Triple<Case,String,Boolean>>> = _userCasesWithAppointments

    private val _usersColaboratorsandStudents = MutableStateFlow<List<Usuario>>(emptyList())
    val usersColaboratorsandStudents: StateFlow<List<Usuario>?> = _usersColaboratorsandStudents

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userType = MutableStateFlow<String?>(null)
    val userType: StateFlow<String?> = _userType

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _abogadoName = MutableStateFlow<String>("")
    val abogadoName: StateFlow<String> = _abogadoName

    private val _estudianteName = MutableStateFlow<String>("")
    val estudianteName: StateFlow<String> = _estudianteName

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
                val type = repository.getUserTypeById(id)
                _userType.value = type
            }
            catch (e: Exception)
            {
                _error.value = "Error al obtener el tipo del usuario: ${e.message}"
            }
        }
    }

    fun fetchUserCasesWithLastAppointmentDetails(userId: String)
    {
        viewModelScope.launch {
            try {
                // Obtener los IDs de casos del usuario
                val caseIds = repository.getUserCasesById(userId)

                // Crear una lista para almacenar los pares de Caso y la información de la última cita (fecha y confirmación)
                val caseWithLastAppointmentDetailsList = mutableListOf<Triple<Case, String, Boolean>>() // Aquí agregamos la fecha y si está confirmada

                // Iterar sobre los IDs de casos y obtener la información completa de cada caso
                caseIds.forEach { caseId ->
                    val case = caseViewModel.repository.getCaseById(caseId) // Obtener caso por ID
                    if (case != null) {
                        // Aquí traemos las citas asociadas al caso
                        val appointmentIds = case.listAppointments

                        if (appointmentIds.isNotEmpty()) {
                            // Obtener el último appointmentId
                            val lastAppointmentId = appointmentIds.last()

                            // Obtener la información de la última cita asociada al caso
                            val lastAppointment = appointmentViewModel.repository.getAppointmentById(lastAppointmentId)

                            if (lastAppointment != null) {
                                // Agregar el par de Caso con la fecha de la última cita y si está confirmada
                                val appointmentDate = lastAppointment.fecha.toDate().toString() // Convertimos la fecha a String
                                val isConfirmed = lastAppointment.confirmed // Campo de confirmación

                                caseWithLastAppointmentDetailsList.add(Triple(case, appointmentDate, isConfirmed))
                            } else {
                                _error.value = "Error al obtener la última cita del caso con ID: $caseId"
                            }
                        } else {
                            // Si no hay citas asociadas al caso
                            caseWithLastAppointmentDetailsList.add(Triple(case, "Sin citas", false)) // "Sin citas" y no confirmada
                        }
                    } else {
                        _error.value = "Error al obtener información del caso con ID: $caseId"
                    }
                }
                // Actualizar la lista de pares de casos completos con la fecha y confirmación de la última cita en el MutableStateFlow
                _userCasesWithAppointments.value = caseWithLastAppointmentDetailsList
            } catch (e: Exception) {
                _error.value = "Error al obtener los casos completos: ${e.message}"
            }
        }
    }

    suspend fun fetchUserByNameCaseId(caseId: String, type: String): String
    {
        val user  = repository.getUserByCaseId(caseId,type)

        if (user != null)
        {
            return user.nombre + " " + user.apellidos
        }
        return ""
    }

    suspend fun fetchColaboratorsandStudents()
    {
        viewModelScope.launch {
            try
            {
                val colaboratorsandstudents = repository.getCollaboratorsAndStudents()
                _usersColaboratorsandStudents.value = colaboratorsandstudents
            }
            catch (e: Exception)
            {
                _error.value = "Error al obtener el tipo del usuario: ${e.message}"
            }
        }
    }

    // Función para obtener el nombre del usuario por su ID y tipo
    fun fetchColaboratorOrStudentById(userId: String, type: String) {
        viewModelScope.launch {
            val userName = repository.getUserInfo(userId) // Supón que el repositorio tiene esta función
            when (type) {
                "colaborador" -> _abogadoName.value =
                    (userName.nombre + " " + userName.apellidos) ?: "Desconocido"
                "estudiante" -> _estudianteName.value =
                    (userName.nombre + " " + userName.apellidos) ?: "Desconocido"
            }
        }
    }
}


