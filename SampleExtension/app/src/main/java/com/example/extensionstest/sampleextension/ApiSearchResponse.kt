package com.example.extensionstest.sampleextension

data class ApiSearchResponse(
    val total: Int,
    val data: List<SearchResult>
)

data class SearchResult(
    val id: Int,
    val slug: String,
    val title: String,
    val type: String,
    val episodes: Int,
    val status: String,
    val season: String,
    val year: Int,
    val score: Float,
    val poster: String,
    val session: String,
    val relevance: String,
)