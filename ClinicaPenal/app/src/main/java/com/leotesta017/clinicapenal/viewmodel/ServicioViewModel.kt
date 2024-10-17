package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.Servicio
import com.leotesta017.clinicapenal.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServicioViewModel : ViewModel() {

    private val repository = ServicioRepository()

    // Estado que contiene la lista de servicios básicos
    private val _servicios = MutableStateFlow<List<Servicio>>(emptyList())
    val servicios: StateFlow<List<Servicio>> = _servicios

    // Estado que contiene el contenido de un servicio
    private val _contenido = MutableStateFlow<String>("")
    val contenido: StateFlow<String> = _contenido

    private val _isSuccess = MutableStateFlow<Boolean?>(null)
    val isSuccess: StateFlow<Boolean?> = _isSuccess

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    // Método para obtener los servicios
    init {
        fetchServicios()
    }

    private fun fetchServicios() {
        viewModelScope.launch {
            try {
                val serviciosList = repository.getServicios()
                _servicios.value = serviciosList
            } catch (e: Exception) {
                _error.value = "Error al cargar los servicios"
            }
        }
    }

    // Método para obtener el contenido de un servicio por su ID
    fun fetchContenidoById(servicioId: String) {
        viewModelScope.launch {
            try {
                val contenido = repository.getContenidoById(servicioId)
                _contenido.value = contenido
            } catch (e: Exception) {
                _error.value = "Error al cargar el contenido"
            }
        }
    }

    // Método para agregar un nuevo servicio
    fun addServicio(servicio: Servicio) {
        viewModelScope.launch {
            try {
                val result = repository.addServicio(servicio)
                _isSuccess.value = result
            } catch (e: Exception) {
                _error.value = "Error al agregar el servicio"
            }
        }
    }

    // Metodo para actualizar un servicio
    fun updateServicio(servicio: Servicio) {
        viewModelScope.launch {
            try {
                val result = repository.updateServicio(servicio)
                _isSuccess.value = result
            } catch (e: Exception) {
                _error.value = "Error al actualizar el servicio"
            }
        }
    }

    fun deleteServicios(servicioId: String) {
        viewModelScope.launch {
            try {
                val result = repository.deleteServicios(servicioId)
                _isSuccess.value = result  // Actualizamos el estado de éxito o fallo
            } catch (e: Exception) {
                _error.value = "Error al eliminar la categoría"
            }
        }
    }


}

