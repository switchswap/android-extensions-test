package com.example.extensionstest.sampleextension

import android.util.Log
import com.example.extensionstest.extensionstestlib.*
import okhttp3.OkHttpClient
import okhttp3.Request

class SampleSource: Source {
    private val client = OkHttpClient()

    override val metadata = SourceMetadata(
        name="Sample Source",
        lang="English",
        websiteUrl="https://animepahe.com/"
    )

    override fun findAnimeEntryInSource(anime: Anime): List<SourceAnime> {
        val searchRequest = Request.Builder()
            .url("https://animepahe.com/api?m=search&l=8&q=${anime.titleRomaji}")
            .build()

        client.newCall(searchRequest).execute().use { response ->
            if (!response.isSuccessful) return emptyList()

            Log.d("ASDF", response.body!!.string())
        }

        return emptyList()
    }

    override fun getEpisodeList(entry: SourceAnime): List<SourceEpisode> {
        TODO("Not yet implemented")
    }

    override fun getMediaStreamForEpisode(episode: SourceEpisode): MediaTrack {
        TODO("Not yet implemented")
    }
}