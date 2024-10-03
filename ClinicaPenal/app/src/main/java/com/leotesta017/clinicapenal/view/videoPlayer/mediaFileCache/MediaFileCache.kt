@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.MimeTypes

@Suppress("DEPRECATION")
class MediaFileCache(private val maxSize: Int) : LinkedHashMap<String, CacheMediaItem>(16, 0.75f, true) {

    // Controlar el tamaño del caché
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, CacheMediaItem>?): Boolean {
        return size > maxSize
    }

    // Obtener o crear un nuevo MediaItem
    fun getOrCreateMediaItem(videoUrl: String): CacheMediaItem {
        return if (containsKey(videoUrl)) {
            get(videoUrl)!! // Si ya está en el caché, devolverlo
        } else {
            // Crear un nuevo MediaItem y añadirlo al caché
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoUrl))
                .setMimeType(MimeTypes.VIDEO_MP4)
                .build()

            val cachedMediaItem = CacheMediaItem(mediaItem)
            put(videoUrl, cachedMediaItem)

            // Si el tamaño del caché excede el máximo, eliminar el más antiguo
            if (size > maxSize) {
                remove(entries.iterator().next().key)
            }

            cachedMediaItem
        }
    }

    // Método para actualizar la posición del video en el caché
    fun updateVideoPosition(videoUrl: String, position: Long) {
        if (containsKey(videoUrl)) {
            get(videoUrl)?.videoPosition = position
        }
    }

    // Liberar un MediaItem del caché
    fun releaseMediaItem(videoUrl: String) {
        remove(videoUrl)
    }

    // Limpiar todo el caché
    fun clearCache() {
        clear()
    }
}


