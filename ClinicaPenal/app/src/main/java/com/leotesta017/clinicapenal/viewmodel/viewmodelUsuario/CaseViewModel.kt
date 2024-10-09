package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import com.leotesta017.clinicapenal.repository.userRepository.AppointmentRepository
import com.leotesta017.clinicapenal.repository.userRepository.CaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Comment

class CaseViewModel : ViewModel() {

    val repository = CaseRepository()
    val appointmentRepository = AppointmentRepository()

    private val _case = MutableStateFlow<Case?>(null)
    val case: StateFlow<Case?> = _case

    private val _unrepresentedCasesWithLastAppointment = MutableStateFlow<List<Triple<Case, String, Boolean>>>(emptyList())
    val unrepresentedCasesWithLastAppointment: StateFlow<List<Triple<Case, String, Boolean>>> = _unrepresentedCasesWithLastAppointment

    private val _caseWithDetails = MutableStateFlow<Pair<Case, Triple<List<Appointment>, List<Comment>, List<ExtraInfo>>>?>(null)
    val caseWithDetails: StateFlow<Pair<Case, Triple<List<Appointment>, List<Comment>, List<ExtraInfo>>>?> = _caseWithDetails


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

    fun fetchUnrepresentedCasesWithLastAppointment() {
        viewModelScope.launch {
            try {
                val cases = repository.getAllUnrepresentedCasesWithLastAppointment(appointmentRepository)
                _unrepresentedCasesWithLastAppointment.value = cases
            } catch (e: Exception) {
                _error.value = "Error al cargar los casos sin representación: ${e.message}"
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
    fun discardCase(caseId: String) {
        viewModelScope.launch {
            val success = repository.updateCaseToDiscard(caseId)
            if (!success) {
                _error.value = "Error al eliminar el caso o elementos asociados."  // Indicar que el caso ha sido eliminado con éxito
            }
        }
    }

    fun fetchCaseWithDetails(caseId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getCaseWithDetails(caseId)
                if (result != null) {
                    _caseWithDetails.value = result
                } else {
                    _error.value = "El caso no se encontró o no tiene detalles asociados"
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener el caso: ${e.message}"
            }
        }
    }

}
