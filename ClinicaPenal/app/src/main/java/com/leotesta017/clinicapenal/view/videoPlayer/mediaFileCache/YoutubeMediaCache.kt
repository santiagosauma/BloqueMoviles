package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

class YoutubeMediaCache(private val maxSize: Int) : LinkedHashMap<String, Float>(16, 0.75f, true){

    // Limitar el tamaño máximo del cache
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Float>?): Boolean {
        return size > maxSize
    }

    // Guardar la posición del video
    fun savePosition(videoUrl: String, position: Float) {
        put(videoUrl, position)
    }

    // Obtener la posición guardada del video
    fun getPosition(videoUrl: String): Float? {
        return get(videoUrl)
    }

    // Limpiar el cache cuando sea necesario
    fun clearCache() {
        clear()
    }
}