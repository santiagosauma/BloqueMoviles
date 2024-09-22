package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.Ejemplo
import com.leotesta017.clinicapenal.model.Recursos
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

    // Método para obtener recursos y ejemplos de un servicio específico
    suspend fun fetchRecursosYejemplos(servicioId: String): Pair<List<Recursos>, List<Ejemplo>> {
        return repository.getRecursosYejemplosDeServicio(servicioId)
    }
}
