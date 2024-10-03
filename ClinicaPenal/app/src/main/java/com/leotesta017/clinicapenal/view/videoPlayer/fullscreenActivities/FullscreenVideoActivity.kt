@file:Suppress("DEPRECATION")

package com.leotesta017.clinicapenal.view.videoPlayer.fullscreenActivities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.leotesta017.clinicapenal.view.videoPlayer.googleDrivePlayer.GoogleDriveVideoPlayer


class FullscreenVideoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el URL del video desde el intent
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: return

        setContent {
                FullscreenVideoActivityScreen(videoUrl = videoUrl)
            }
        }


    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
}

@Composable
fun FullscreenVideoActivityScreen(videoUrl: String) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val videoId = formatGoogleDriveUrl(videoUrl)
    val activity = LocalContext.current as? Activity

    // Estado para controlar la visibilidad del ícono de salida
    var isIconVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (videoId != null) {
            GoogleDriveVideoPlayer(
                videoUrl = videoUrl,
                isVisible = true,
                onControllerVisibilityChanged = { isVisible -> isVisible.also { isIconVisible = it } }
            )
        } else {
            Text("Invalid GoogleDrive URL", color = Color.Red)
        }

        // Mostrar la flecha solo cuando los controles del video son visibles
        if (isIconVisible) {
            IconButton(
                onClick = { activity?.finish() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Salir de pantalla completa",
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}



fun formatGoogleDriveUrl(url: String): String? {
    val regex = Regex(".*?/file/d/(.*?)/.*")
    val matchResult = regex.find(url)
    val fileId = matchResult?.groupValues?.get(1)
    return fileId?.let {
        "https://www.googleapis.com/drive/v3/files/$fileId?alt=media&key=${com.leotesta017.clinicapenal.BuildConfig.API_KEY}"
    }
}
