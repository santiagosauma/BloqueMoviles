@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.MimeTypes

@Suppress("DEPRECATION")
class MediaFileCache(private val maxSize: Int) : LinkedHashMap<String, MediaItem>(16, 0.75f, true) {

    // Sobrescribir para controlar el tamaño del caché
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, MediaItem>?): Boolean {
        val shouldRemove = size > maxSize
        if (shouldRemove) {
            Log.d("MediaFileCache", "Removing eldest entry: ${eldest?.key}")
        }
        return shouldRemove
    }

    fun getOrCreateMediaItem(videoUrl: String): MediaItem {
        return if (containsKey(videoUrl)) {
            // Si ya está en el caché, devolverlo
            Log.d("MediaFileCache", "Cache hit for URL: $videoUrl")
            get(videoUrl)!!
        } else {
            // Si no está en el caché, crear un nuevo MediaItem
            Log.d("MediaFileCache", "Cache miss for URL: $videoUrl. Creating new MediaItem.")
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(videoUrl))
                .setMimeType(MimeTypes.VIDEO_MP4)
                .build()

            // Añadirlo al caché
            put(videoUrl, mediaItem)

            // Si el tamaño excede el máximo, eliminar el más antiguo
            if (size > maxSize) {
                val oldestEntry = entries.iterator().next()
                Log.d("MediaFileCache", "Cache size exceeded. Removing oldest entry: ${oldestEntry.key}")
                remove(oldestEntry.key)
            }

            // Devolver el nuevo MediaItem
            return mediaItem
        }
    }

    // Liberar un MediaItem
    fun releaseMediaItem(videoUrl: String) {
        if (containsKey(videoUrl)) {
            Log.d("MediaFileCache", "Releasing MediaItem for URL: $videoUrl")
            remove(videoUrl)
        } else {
            Log.d("MediaFileCache", "No MediaItem to release for URL: $videoUrl")
        }
    }

    // Limpiar todo el caché
    fun clearCache() {
        Log.d("MediaFileCache", "Clearing the entire cache")
        clear()
    }
}


