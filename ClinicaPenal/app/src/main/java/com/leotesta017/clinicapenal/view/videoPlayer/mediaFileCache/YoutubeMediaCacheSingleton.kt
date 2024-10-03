package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

object YoutubeMediaCacheSingleton {
    val youtubeCache: YoutubeMediaCache = YoutubeMediaCache(maxSize = 10)
}