package com.example.extensionstest.extensionstestlib

data class Anime(
    val provider: String,
    val provider_anime_id: Int,
    var titleNative: String,
    val titleEnglish: String,
    val titleRomaji: String,
    val coverUrl: String,
    val bannerUrl: String,
    val description: String,
    val status: AnimeStatus,
    val episodeCount: Int,
    val rating: Float,
    val tags: List<String>,
)

enum class AnimeStatus {
    FINISHED, RELEASING, NOT_YET_RELEASED, CANCELLED, HIATUS
}