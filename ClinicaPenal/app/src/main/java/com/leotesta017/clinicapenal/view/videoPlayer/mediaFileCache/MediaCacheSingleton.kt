package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

object MediaCacheSingleton {
    val mediaFileCache: MediaFileCache = MediaFileCache(maxSize = 5)
}