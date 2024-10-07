package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import com.leotesta017.clinicapenal.repository.userRepository.Case_CounterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Case_CounterViewModel: ViewModel() {
    private val repository = Case_CounterRepository()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Función para buscar o agregar un caso y obtener su posición
    suspend fun findOrAddCase(caseId: String): Int {
        return try {
            repository.findOrAddCase(caseId)
        } catch (e: Exception) {
            _error.value = "Error al obtener el índice del caso: ${e.message}"
            -1 // Devuelve -1 en caso de error
        }
    }
}

