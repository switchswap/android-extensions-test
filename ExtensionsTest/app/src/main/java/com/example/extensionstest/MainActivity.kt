package com.example.extensionstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.extensionstest.databinding.ActivityMainBinding
import com.example.extensionstest.extensionstestlib.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext =
        Dispatchers.Main + SupervisorJob()

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext[Job]!!.cancel()
    }

    lateinit var binding: ActivityMainBinding;
    lateinit var source: Source;

    var anime: MutableList<SourceAnime> = mutableListOf();
    var episodes: MutableList<SourceEpisode> = mutableListOf();

    var selectedAnime = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        source = ExtensionLoader.loadSources(this).first();

        binding.sourceAnimeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(anime, { view -> onAnimeItemClick(view) })
        }

        binding.sourceEpisodeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ListAdapter(episodes, { view -> onEpisodeItemClick(view) })
        }

        binding.animeFindButton.setOnClickListener {
            onFindButtonClick();
        }
    }

    fun onFindButtonClick() {
        val providerAnime = Anime(
            provider = "",
            provider_anime_id = 0,
            titleNative = "",
            titleEnglish = "",
            titleRomaji = binding.animeInput.text.toString(),
            coverUrl = "",
            bannerUrl = "",
            description = "",
            status = AnimeStatus.FINISHED,
            episodeCount = 13,
            rating = 7.75f,
            tags = emptyList<String>(),
        )

        getAnime(providerAnime)
    }

    fun onAnimeItemClick(item: View) {
        val itemIndex: Int = binding.sourceAnimeList.getChildLayoutPosition(item);
        selectedAnime = itemIndex

        getEpisodes(anime[itemIndex])
    }

    fun onEpisodeItemClick(item: View) {
        val itemIndex: Int = binding.sourceEpisodeList.getChildLayoutPosition(item);

        getMediaTrack(anime[selectedAnime], episodes[itemIndex])
    }

    fun getAnime(providerAnime: Anime) {
        Log.d("ASDF", "getAnime called")
        this.launch {
            withContext(Dispatchers.IO) {
                anime.clear()
                anime.addAll(source.findAnimeInSource(providerAnime))
            }
        }.invokeOnCompletion {
            binding.sourceAnimeList.adapter?.notifyDataSetChanged()
        }
    }

    fun getEpisodes(sourceAnime: SourceAnime) {
        Log.d("ASDF", "getEpisodes called")
        this.launch {
            withContext(Dispatchers.IO) {
                episodes.clear()
                episodes.addAll(source.getEpisodeList(sourceAnime))
            }
        }.invokeOnCompletion {
            binding.sourceEpisodeList.adapter?.notifyDataSetChanged()
        }
    }

    fun getMediaTrack(sourceAnime: SourceAnime, sourceEpisode: SourceEpisode) {
        Log.d("ASDF", "getMediaTrack called")
        this.launch {
            withContext(Dispatchers.IO) {
                val stream = source.getMediaStreamForEpisode(sourceAnime, sourceEpisode)

            }
        }
    }
}