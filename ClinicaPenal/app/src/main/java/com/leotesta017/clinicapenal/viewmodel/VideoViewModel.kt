package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _deleteVideoResult = MutableLiveData<Boolean>()
    val deleteVideoResult: LiveData<Boolean> get() = _deleteVideoResult

    // Método para obtener los videos
    init {
        fetchVideos()
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            try {
                val videoList = repository.getVideos()
                _videos.value = videoList
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar los videos"
            }
        }
    }

    /**
     * Método para obtener un video específico por su ID.
     *
     * @param videoId El ID del video a obtener.
     * @return El objeto Video si se encuentra, null en caso contrario.
     */
    suspend fun getVideoById(videoId: String): Video? {
        return try {
            repository.getVideoById(videoId)
        } catch (e: Exception) {
            _error.value = "Error al obtener el video"
            null
        }
    }

    /**
     * Método para actualizar un video existente.
     *
     * @param videoId El ID del video a actualizar.
     * @param updatedVideo El objeto Video con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    fun updateVideo(videoId: String, updatedVideo: Video, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val success = repository.updateVideo(videoId, updatedVideo)
                if (success) {
                    // Opcional: Actualizar la lista de videos después de una actualización exitosa
                    fetchVideos()
                }
                onResult(success)
            } catch (e: Exception) {
                _error.value = "Error al actualizar el video"
                onResult(false)
            }
        }
    }

    // Función para eliminar un video por su ID
    fun deleteVideo(videoId: String) {
        viewModelScope.launch {
            try {
                // Llamada al repositorio para eliminar el video
                val result = repository.deleteVideoById(videoId)
                _deleteVideoResult.postValue(result) // Actualizamos el LiveData con el resultado
            } catch (e: Exception) {
                _deleteVideoResult.postValue(false) // Si ocurre algún error, se informa que no fue exitoso
            }
        }
    }

}

