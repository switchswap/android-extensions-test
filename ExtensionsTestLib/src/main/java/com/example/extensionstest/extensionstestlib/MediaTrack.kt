package com.example.extensionstest.extensionstestlib

// MediaTrack defines a single video url and the required info to access it
// (such as the type, http headers etc.)
sealed class MediaTrack {
    abstract val mediaUrl: String

    // type for HLS type streams (usually m3u8 playlist files)
    data class HLS(
        override val mediaUrl: String,
        val headers: HashMap<String, String> = hashMapOf<String, String>()
    ): MediaTrack()

    // type for mp4 or other media files, optionally needs Ranged or other http headers to play
    data class Http(
        override val mediaUrl: String,
        val headers: HashMap<String, String> = hashMapOf<String, String>()
    ): MediaTrack()

    // type for locally loaded files
    data class Local(
        override val mediaUrl: String
    ): MediaTrack()
}