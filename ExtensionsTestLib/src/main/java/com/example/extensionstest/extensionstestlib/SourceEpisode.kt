package com.example.extensionstest.extensionstestlib

data class SourceEpisode(
    val episodeNumber: String,
    val title: String,
    val description: String,
    // url to the player website for this episode,
    // this is passed back to the source when trying to play the episode
    val sourceIdentifier: String
)