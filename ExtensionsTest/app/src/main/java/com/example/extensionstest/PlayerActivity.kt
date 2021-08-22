package com.example.extensionstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.extensionstest.databinding.ActivityPlayerBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import android.content.Intent
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource










class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)

        val streamUrl = intent.getStringExtra("STREAM_HLS")
        val headers = intent.getSerializableExtra("STREAM_HEADERS") as HashMap<String, String>

        Toast.makeText(this,"Url: $streamUrl, Headers: $headers",Toast.LENGTH_SHORT).show()

        streamUrl?.let { url ->
            // build exoplayer and load source
            val player = SimpleExoPlayer.Builder(this).build()
            binding.exoplayer.player = player

            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory().setDefaultRequestProperties(headers)

            val hlsMediaSource: HlsMediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))

            player.setMediaSource(hlsMediaSource)
            player.prepare()
        }

        setContentView(binding.root)
    }
}