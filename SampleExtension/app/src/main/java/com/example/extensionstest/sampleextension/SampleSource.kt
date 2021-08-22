package com.example.extensionstest.sampleextension

import android.util.Log
import com.example.extensionstest.extensionstestlib.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class SampleSource: Source {
    private val client = OkHttpClient()
    private val gson = Gson()

    private val kwikRegex = Regex("uwu\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)\\|(\\w+)")

    override val metadata = SourceMetadata(
        name="Sample Source",
        lang="English",
        websiteUrl="https://animepahe.com/"
    )

    override fun findAnimeInSource(animeInfo: Anime): List<SourceAnime> {
        val searchRequest = Request.Builder()
            .url("https://animepahe.com/api?m=search&l=8&q=${animeInfo.titleRomaji}")
            .build()

        client.newCall(searchRequest).execute().use { resp ->
            if (!resp.isSuccessful) return emptyList()

            val searchResponse: ApiSearchResponse = gson.fromJson(resp.body?.string(), ApiSearchResponse::class.java)

            return searchResponse.data.map { result -> SourceAnime(result.title, result.id.toString()) }
        }
    }

    override fun getEpisodeList(anime: SourceAnime): List<SourceEpisode> {
        val episodeRequest = Request.Builder()
            .url("https://animepahe.com/api?m=release&id=${anime.sourceIdentifier}&sort=episode_asc")
            .build()

        client.newCall(episodeRequest).execute().use { resp ->
            if (!resp.isSuccessful) return emptyList()

            val episodeResponse: ApiEpisodeResponse = gson.fromJson(resp.body?.string(), ApiEpisodeResponse::class.java)

            return episodeResponse.data.map { result -> SourceEpisode(result.episode.toString(), result.title, "", result.session) }
        }
    }

    override fun getMediaStreamForEpisode(anime: SourceAnime, episode: SourceEpisode): MediaTrack? {
//        // refetch episodes again because we need a fresh episode "session"
//        val episodes = getEpisodeList(SourceAnime("", episode.sourceIdentifier))
//        // new episode data for the same episode as desired
//        val newEpisodeData = episodes.find { it.episodeNumber == episode.episodeNumber }

        val streamRequest = Request.Builder()
            .url("https://animepahe.com/api?m=links&id=${anime.sourceIdentifier}&session=${episode.sourceIdentifier}")
            .build()

        client.newCall(streamRequest).execute().use { resp ->
            if (!resp.isSuccessful) return null

            val streamResponse: ApiStreamResponse = gson.fromJson(resp.body?.string(), ApiStreamResponse::class.java)

            streamResponse.data.last().stream.let { stream ->

                Log.d("ASDF", "kwik url: ${stream.kwik}")

                val kwikRequest = Request.Builder()
                    .url(stream.kwik)
                    .addHeader("Referer", "https://animepahe.com/")
                    .build()

                client.newCall(kwikRequest).execute().use { resp ->

                    Log.d("ASDF", "kwikRequest: ${resp.code}")

                    resp.body?.let { kwikResp ->

                        val body = kwikResp.string()

                        Log.d("ASDF", this.kwikRegex.find(body).toString())

                        this.kwikRegex.find(body)?.groupValues?.let {
                            Log.d("ASDF", "kwik regex")

                            val parsedUrl = "${it[9]}://${it[8]}-${it[7]}.${it[6]}.${it[5]}.${it[4]}/${it[3]}/${it[2]}/${it[1]}/uwu.m3u8"

                            Log.d("ASDF", parsedUrl)
                            return MediaTrack.HLS(parsedUrl, hashMapOf("Referer" to "https://kwik.cx/"))
                        }
                    }

                    return null
                }

            }
        }
    }
}