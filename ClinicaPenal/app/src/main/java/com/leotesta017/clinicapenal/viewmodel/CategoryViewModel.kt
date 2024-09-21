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

    // Estado que contiene la lista de categorías
    private val _categories = MutableStateFlow<List<Categoria>>(emptyList())
    val categories: StateFlow<List<Categoria>> = _categories

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener las categorías
    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categoryList = repository.getCategories()
                _categories.value = categoryList
            } catch (e: Exception) {
                _error.value = "Error al cargar las categorías"
            }
        }
    }
}
