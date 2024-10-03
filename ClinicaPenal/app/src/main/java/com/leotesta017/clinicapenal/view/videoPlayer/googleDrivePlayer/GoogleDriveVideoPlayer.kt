@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.videoPlayer.googleDrivePlayer

import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache.MediaCacheSingleton
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.formatGoogleDriveUrl

@Composable
fun GoogleDriveVideoPlayer(
    videoUrl: String,
    isVisible: Boolean,
    onControllerVisibilityChanged: (Boolean) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val formattedUrl = formatGoogleDriveUrl(videoUrl) ?: videoUrl

    val cache = MediaCacheSingleton.mediaFileCache

    var isBuffering by remember { mutableStateOf(true) }
    var exoPlayer: ExoPlayer? by remember { mutableStateOf(null) }

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    val videoHeight = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        screenHeightDp
    } else {
        screenWidthDp * 9 / 16
    }

    val density = LocalDensity.current

    DisposableEffect(lifecycleOwner, isVisible) {
        if (isVisible) {
            val cachedMediaItem = cache.getOrCreateMediaItem(formattedUrl)
            exoPlayer?.release()
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                setMediaItem(cachedMediaItem.mediaItem)
                prepare()

                seekTo(cachedMediaItem.videoPosition)

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_BUFFERING -> {
                                isBuffering = true
                            }
                            Player.STATE_READY -> {
                                isBuffering = false
                            }
                            Player.STATE_ENDED -> {
                                cache.updateVideoPosition(formattedUrl, exoPlayer?.currentPosition ?: 0)
                            }

                            Player.STATE_IDLE -> {
                            }
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onPositionDiscontinuity(reason: Int) {
                        super.onPositionDiscontinuity(reason)

                        if (reason == Player.DISCONTINUITY_REASON_SEEK) {
                            cache.updateVideoPosition(formattedUrl, exoPlayer?.currentPosition ?: 0)
                        }
                    }
                })
            }
        }

        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer?.playWhenReady = false
                    cache.updateVideoPosition(formattedUrl, exoPlayer?.currentPosition ?: 0)
                }
                Lifecycle.Event.ON_DESTROY -> {
                    exoPlayer?.release()
                    exoPlayer = null
                }
                else -> Unit
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            cache.updateVideoPosition(formattedUrl, exoPlayer?.currentPosition ?: 0)
            lifecycle.removeObserver(observer)
            exoPlayer?.release()
            exoPlayer = null

        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(videoHeight)
    ) {
        exoPlayer?.let { player ->
            AndroidView(
                factory = { context ->
                    FrameLayout(context).apply {
                        val playerView = PlayerView(context).apply {
                            this.player = player
                            useController = true
                            controllerShowTimeoutMs = 3000
                            setControllerVisibilityListener { visibility ->
                                onControllerVisibilityChanged(visibility == View.VISIBLE)
                            }
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        }
                        this.addView(playerView)
                    }
                },
                update = { view ->
                    view.layoutParams = view.layoutParams.apply {
                        width = ViewGroup.LayoutParams.MATCH_PARENT
                        height = with(density) { videoHeight.roundToPx() }
                    }
                },
                modifier = Modifier.fillMaxHeight()
            )
        }

        if (isBuffering) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

