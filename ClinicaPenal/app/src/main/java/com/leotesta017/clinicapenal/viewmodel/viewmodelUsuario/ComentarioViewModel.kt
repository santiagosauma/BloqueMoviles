package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Comentario
import com.leotesta017.clinicapenal.repository.userRepository.ComentarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComentarioViewModel : ViewModel() {

    private val repository = ComentarioRepository()

    private val _comentario = MutableStateFlow<Comentario?>(null)
    val comentario: StateFlow<Comentario?> = _comentario

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener un comentario por su ID
    fun fetchComentario(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getComentarioById(id)
                if (result != null) {
                    _comentario.value = result
                } else {
                    _error.value = "Comentario no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el comentario: ${e.message}"
            }
        }
    }

    // Agregar un nuevo comentario
    fun addNewComentario(comentario: Comentario, userId: String) {
        viewModelScope.launch {
            val success = repository.addComentario(comentario, userId)
            if (!success) {
                _error.value = "Error al agregar el comentario"
            }
        }
    }

    // Actualizar un comentario existente
    fun updateComentario(id: String, comentarioData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateComentario(id, comentarioData)
            if (!success) {
                _error.value = "Error al actualizar el comentario"
            }
        }
    }
}
