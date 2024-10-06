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

    private val repository = UsuarioRepository()
    private val caseViewModel = CaseViewModel()

    private val _usuario = MutableStateFlow<Usuario>(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _userCases = MutableStateFlow<List<Case>>(emptyList())  // Cambiamos a lista de Case
    val userCases: StateFlow<List<Case>> = _userCases

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


    // Obtener los casos completos de un usuario
    fun fetchUserCasesWithDetails(userId: String) {
        viewModelScope.launch {
            try {
                // Obtener los IDs de casos del usuario
                val caseIds = repository.getUserCasesById(userId)

                // Crear una lista para almacenar los casos
                val cases = mutableListOf<Case>()

                // Iterar sobre los IDs de casos y obtener la información completa de cada caso
                caseIds.forEach { caseId ->
                    val case = caseViewModel.repository.getCaseById(caseId) // Obtener caso por ID
                    if (case != null) {
                        cases.add(case)
                    } else {
                        _error.value = "Error al obtener información del caso con ID: $caseId"
                    }
                }

                // Actualizar la lista de casos completos en el MutableStateFlow
                _userCases.value = cases

            } catch (e: Exception) {
                _error.value = "Error al obtener los casos completos: ${e.message}"
            }
        }
    }
}
