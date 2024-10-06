package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.repository.userRepository.CaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CaseViewModel : ViewModel() {

    val repository = CaseRepository()

    private val _case = MutableStateFlow<Case?>(null)
    val case: StateFlow<Case?> = _case

    private val _caseDeleted = MutableStateFlow<Boolean>(false)
    val caseDeleted: StateFlow<Boolean> = _caseDeleted

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener un caso por su ID
    fun fetchCase(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getCaseById(id)
                if (result != null) {
                    _case.value = result
                } else {
                    _error.value = "Caso no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el caso: ${e.message}"
            }
        }
    }

    // Agregar un nuevo caso
    fun addNewCase(case: Case, userId: String) {
        viewModelScope.launch {
            val success = repository.addCase(case, userId)
            if (!success) {
                _error.value = "Error al agregar el caso"
            }
        }
    }

    // Asignar abogado o estudiante al caso
    fun assignUserToCase(caseId: String, userId: String, role: String) {
        viewModelScope.launch {
            val success = repository.assignUserToCase(caseId, userId, role)
            if (!success) {
                _error.value = "Error al asignar $role: Verifique el tipo de usuario o el ID"
            }
        }
    }

    // Actualizar un caso existente
    fun updateCase(id: String, caseData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateCase(id, caseData)
            if (!success) {
                _error.value = "Error al actualizar el caso"
            }
        }
    }

    // Función para eliminar un caso y sus elementos asociados
    fun deleteCase(caseId: String) {
        viewModelScope.launch {
            val success = repository.deleteCase(caseId)
            if (!success) {
                _error.value = "Error al eliminar el caso o elementos asociados."  // Indicar que el caso ha sido eliminado con éxito
            }
        }
    }

}
