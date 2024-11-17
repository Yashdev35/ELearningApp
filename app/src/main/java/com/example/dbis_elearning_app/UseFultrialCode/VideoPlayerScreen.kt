package com.example.dbis_elearning_app.UseFultrialCode

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.dbis_elearning_app.R

@OptIn(UnstableApi::class)
@Composable
fun CloudinaryVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Create an ExoPlayer instance
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = true
            prepare()
        }
    }

    // Handle lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Create the player view
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = true // Show playback controls
                setShowNextButton(false)
                setShowPreviousButton(false)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f) // Standard video aspect ratio
    )
}

// Example usage with your specific Cloudinary URL
@Composable
fun VideoScreen(link: String = stringResource(id = R.string.demoVideoUrl)) {
    CloudinaryVideoPlayer(
        videoUrl = link,
        modifier = Modifier.fillMaxWidth()
    )
}