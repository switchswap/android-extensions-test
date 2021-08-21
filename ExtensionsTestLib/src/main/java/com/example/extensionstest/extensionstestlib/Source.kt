package com.example.extensionstest.extensionstestlib

// additional info for a single source
data class SourceMetadata(
    val name: String,
    val lang: String,
    val websiteUrl: String
)


// Interface definition for a single source
interface Source {
    // required metadata for the source
    val metadata: SourceMetadata

    // retrieve one or more anime entries from the source which match
    // the given anime from the provider.
    // Returning multiple entries will allow the user to pick one,
    // returning only one will skip that step.
    fun findAnimeEntryInSource(anime: Anime): List<SourceAnime>

    // retrieve a list of episodes for the given anime,
    // providing episode metadata as well as
    // source-specific links to the video player site of each episode
    fun getEpisodeList(entry: SourceAnime): List<SourceEpisode>

    // retrieve the media stream (hls or video file) for the given episode
    fun getMediaStreamForEpisode(episode: SourceEpisode): MediaTrack
}