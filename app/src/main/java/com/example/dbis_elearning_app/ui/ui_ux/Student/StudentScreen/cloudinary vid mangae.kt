package com.example.dbis_elearning_app.ui.ui_ux.Student.StudentScreen

// CloudinaryVideoManager.kt
import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.common.util.UnstableApi

class CloudinaryVideoManager(private val context: Context) {
    companion object {
        private const val CLOUDINARY_HLS_URL_FORMAT =
            "https://res.cloudinary.com/%s/video/upload/sp_%s/q_auto/%s.m3u8"
    }

    @UnstableApi
    private val trackSelector = DefaultTrackSelector(context).apply {
        setParameters(
            buildUponParameters()
                .setMaxVideoSizeSd() // Default to SD quality
                .setPreferredVideoMimeType(MimeTypes.APPLICATION_M3U8)
        )
    }

    @OptIn(UnstableApi::class)
    fun buildHlsMediaSource(
        cloudName: String,
        publicId: String,
        streamingProfile: String = "full"
    ): HlsMediaSource {
        val hlsUrl = String.format(CLOUDINARY_HLS_URL_FORMAT, cloudName, streamingProfile, publicId)

        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("CloudinaryAndroidPlayer")
            .setAllowCrossProtocolRedirects(true)

        return HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(Uri.parse(hlsUrl)))
    }

    @OptIn(UnstableApi::class)
    fun buildMediaSourceFromUrl(url: String): HlsMediaSource {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("CloudinaryAndroidPlayer")
            .setAllowCrossProtocolRedirects(true)

        return HlsMediaSource.Factory(dataSourceFactory)
            .setAllowChunklessPreparation(true)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))
    }

    @OptIn(UnstableApi::class)
    fun getTrackSelector() = trackSelector
}