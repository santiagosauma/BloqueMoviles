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

    // Estado que contiene la lista de servicios
    private val _servicios = MutableStateFlow<List<Servicio>>(emptyList())
    val servicios: StateFlow<List<Servicio>> = _servicios

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener las servicios
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
}