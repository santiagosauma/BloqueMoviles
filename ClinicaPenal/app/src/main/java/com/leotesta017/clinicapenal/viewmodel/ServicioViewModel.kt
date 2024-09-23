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
    private val _serviciosBasicos = MutableStateFlow<List<Servicio>>(emptyList())
    val serviciosBasicos: StateFlow<List<Servicio>> = _serviciosBasicos

    // Estado que contiene el contenido de un servicio
    private val _contenido = MutableStateFlow<String>("")
    val contenido: StateFlow<String> = _contenido

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener los servicios básicos
    init {
        fetchServiciosBasicos()
    }

    private fun fetchServiciosBasicos() {
        viewModelScope.launch {
            try {
                val serviciosList = repository.getServiciosBasicos()
                _serviciosBasicos.value = serviciosList
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
}

