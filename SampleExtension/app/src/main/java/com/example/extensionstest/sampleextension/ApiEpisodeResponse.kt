package com.example.extensionstest.sampleextension

data class ApiEpisodeResponse(
    val total: Int,
    val per_page: Int,
    val current_page: Int,
    val last_page: Int,
    val next_page_url: String?,
    val prev_page_url: String?,
    val from: Int,
    val to: Int,
    val data: List<EpisodeResult>
)

data class EpisodeResult(
    val id: Int,
    val anime_id: Int,
    val episode: Int,
    val episode2: Int,
    val edition: String,
    val title: String,
    val snapshot: String,
    val disc: String,
    val duration: String,
    val session: String,
    val filler: Int,
    val created_at: String
)