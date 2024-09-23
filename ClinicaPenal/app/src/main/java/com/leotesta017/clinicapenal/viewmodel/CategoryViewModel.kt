package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    // Estado que contiene la lista de categorías básicas
    private val _categoriasBasicas = MutableStateFlow<List<Categoria>>(emptyList())
    val categoriasBasicas: StateFlow<List<Categoria>> = _categoriasBasicas

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener las categorías básicas
    init {
        fetchCategoriasBasicas()
    }

    private fun fetchCategoriasBasicas() {
        viewModelScope.launch {
            try {
                val categoriasList = repository.getCategoriasBasicas()
                _categoriasBasicas.value = categoriasList
            } catch (e: Exception) {
                _error.value = "Error al cargar las categorías"
            }
        }
    }
}
