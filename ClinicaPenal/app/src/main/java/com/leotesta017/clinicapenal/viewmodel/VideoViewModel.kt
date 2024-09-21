package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.Video
import com.leotesta017.clinicapenal.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val repository = VideoRepository()

    // Estado que contiene la lista de videos
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Método para obtener los videos
    init {
        fetchVideos()
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            try {
                val videoList = repository.getVideos()
                _videos.value = videoList
            } catch (e: Exception) {
                _error.value = "Error al cargar los videos"
            }
        }
    }
}
