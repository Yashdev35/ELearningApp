package com.example.dbis_elearning_app.UseFultrialCode//package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen
//
//// CloudinaryVideoPlayer.kt
//import androidx.annotation.OptIn
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.ui.PlayerView
//import androidx.media3.common.Player
//import kotlinx.coroutines.delay
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.media3.common.util.UnstableApi
//
//@OptIn(UnstableApi::class)
//@Composable
//fun CloudinaryVideoPlayer(
//    modifier: Modifier = Modifier,
//    videoConfig: VideoConfig,
//    onQualityChanged: (VideoQuality) -> Unit = {},
//    onError: (Exception) -> Unit = {}
//) {
//    val context = LocalContext.current
//    var showControls by remember { mutableStateOf(true) }
//    var isPlaying by remember { mutableStateOf(false) }
//    var currentQuality by remember { mutableStateOf(VideoQuality.AUTO) }
//
//    val cloudinaryManager = remember { CloudinaryVideoManager(context) }
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .setTrackSelector(cloudinaryManager.getTrackSelector())
//            .build()
//    }
//
//    LaunchedEffect(videoConfig) {
//        try {
//            exoPlayer.apply {
//                val mediaSource = when (videoConfig) {
//                    is VideoConfig.CloudinaryHLS -> cloudinaryManager.buildHlsMediaSource(
//                        videoConfig.cloudName,
//                        videoConfig.publicId,
//                        videoConfig.streamingProfile
//                    )
//                    is VideoConfig.DirectUrl -> cloudinaryManager.buildMediaSourceFromUrl(
//                        videoConfig.url
//                    )
//                }
//                setMediaSource(mediaSource)
//                prepare()
//                playWhenReady = true
//            }
//        } catch (e: Exception) {
//            onError(e)
//        }
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    Box(modifier = modifier) {
//        AndroidView(
//            factory = { context ->
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = false
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(16f/9f)
//        )
//
//        // Custom controls overlay
//        if (showControls) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                // Play/Pause button
//                IconButton(
//                    onClick = {
//                        if (isPlaying) exoPlayer.pause() else exoPlayer.play()
//                        isPlaying = !isPlaying
//                    },
//                    modifier = Modifier.align(Alignment.Center)
//                ) {
//                    Icon(
//                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
//                        contentDescription = if (isPlaying) "Pause" else "Play",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//
//                // Quality selector
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(bottom = 16.dp)
//                ) {
//                    var showQualityMenu by remember { mutableStateOf(false) }
//
//                    IconButton(onClick = { showQualityMenu = true }) {
//                        Icon(
//                            imageVector = Icons.Default.SettingsApplications,
//                            contentDescription = "Quality Settings",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//
//                    DropdownMenu(
//                        expanded = showQualityMenu,
//                        onDismissRequest = { showQualityMenu = false }
//                    ) {
//                        VideoQuality.values().forEach { quality ->
//                            DropdownMenuItem(
//                                text = { Text(quality.label) },
//                                onClick = {
//                                    currentQuality = quality
//                                    onQualityChanged(quality)
//                                    showQualityMenu = false
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// VideoConfig.kt
//sealed class VideoConfig {
//    data class CloudinaryHLS(
//        val cloudName: String,
//        val publicId: String,
//        val streamingProfile: String = "full"
//    ) : VideoConfig()
//
//    data class DirectUrl(
//        val url: String
//    ) : VideoConfig()
//}
//
//enum class VideoQuality(val label: String, val profile: String) {
//    AUTO("Auto", "auto"),
//    HD("HD", "full_hd"),
//    SD("SD", "sd"),
//    LOW("Low", "low")
//}
//
//// Usage Example
//@Composable
//fun VideoScreen2(videoUrl: String) {
//    // For direct HLS URL from backend
////    CloudinaryVideoPlayer(
////        videoConfig = VideoConfig.DirectUrl(videoUrl),
////        modifier = Modifier.fillMaxWidth(),
////        onQualityChanged = { quality ->
////            // Handle quality change
////        },
////        onError = { exception ->
////            // Handle error
////        }
////    )
//
//    // OR for Cloudinary public ID
//    CloudinaryVideoPlayer(
//        videoConfig = VideoConfig.CloudinaryHLS(
//            cloudName = "dcdlxeu52",
//            publicId = "pdaghb8har5govvqpxpa"
//        ),
//        modifier = Modifier.fillMaxWidth()
//    )
//}