package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import com.leotesta017.clinicapenal.repository.userRepository.ExtraInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExtraInfoViewModel : ViewModel() {

    private val repository = ExtraInfoRepository()

    private val _extraInfo = MutableStateFlow<ExtraInfo?>(null)
    val extraInfo: StateFlow<ExtraInfo?> = _extraInfo

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener información extra por su ID
    fun fetchExtraInfo(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getExtraInfoById(id)
                if (result != null) {
                    _extraInfo.value = result
                } else {
                    _error.value = "Información extra no encontrada"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar la información extra: ${e.message}"
            }
        }
    }

    // Agregar nueva información extra
    fun addNewExtraInfo(extraInfo: ExtraInfo, userId: String) {
        viewModelScope.launch {
            val success = repository.addExtraInfo(extraInfo, userId)
            if (!success) {
                _error.value = "Error al agregar la información extra"
            }
        }
    }

    // Actualizar información extra existente
    fun updateExtraInfo(id: String, extraInfoData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateExtraInfo(id, extraInfoData)
            if (!success) {
                _error.value = "Error al actualizar la información extra"
            }
        }
    }
}
