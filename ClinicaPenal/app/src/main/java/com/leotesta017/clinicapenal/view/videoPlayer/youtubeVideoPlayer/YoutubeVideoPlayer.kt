package com.leotesta017.clinicapenal.view.videoPlayer.youtubeVideoPlayer

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities.extractVideoIdFromUrl
import com.leotesta017.clinicapenal.view.videoPlayer.mediaFileCache.YoutubeMediaCacheSingleton
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@SuppressLint("ClickableViewAccessibility")
@Composable
fun YouTubePlayerWithLifecycle(
    videoUrl: String,
    isVisible: Boolean,
    onControllerVisibilityChanged: (Boolean) -> Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoId = extractVideoIdFromUrl(videoUrl)
    var youTubePlayerInstance: YouTubePlayer? = null

    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    val YoutubeCache = YoutubeMediaCacheSingleton.youtubeCache

    // Recuperar la posición guardada desde el cache
    val savedPosition = YoutubeCache.getPosition(videoUrl) ?: 0f

    if (videoId != null) {
        AndroidView(factory = { context ->
            val gestureDetector = GestureDetector(
                context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                        onControllerVisibilityChanged(true)
                        return true
                    }
                }
            )

            val youTubePlayerView = YouTubePlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    false
                }

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayerInstance = youTubePlayer
                        youTubePlayer.cueVideo(videoId, savedPosition) // Reproducir desde la posición guardada
                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                        // Guardar la posición actual del video en el cache
                        YoutubeCache.savePosition(videoUrl, second)
                    }
                })
            }

            lifecycleOwner.lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView
        }, update = { view ->
            view.layoutParams = view.layoutParams.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = with(density) { (screenWidthDp * 9 / 16).toPx().toInt() }
            }
        })

        DisposableEffect(lifecycleOwner, isVisible) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        youTubePlayerInstance?.pause()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        youTubePlayerInstance?.pause()
                    }
                    else -> Unit
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            if (!isVisible) {
                youTubePlayerInstance?.pause()
            }

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    } else {
        Text("Invalid YouTube URL", color = Color.Red)
    }
}
