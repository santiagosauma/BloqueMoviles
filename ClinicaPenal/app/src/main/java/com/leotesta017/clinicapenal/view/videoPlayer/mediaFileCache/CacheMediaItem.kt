package com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache

import com.google.android.exoplayer2.MediaItem

@Suppress("DEPRECATION")
data class CacheMediaItem(
    val mediaItem: MediaItem,
    var videoPosition: Long = 0L
)
