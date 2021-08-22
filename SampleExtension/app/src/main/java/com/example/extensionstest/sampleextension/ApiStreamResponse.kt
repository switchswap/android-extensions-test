package com.example.extensionstest.sampleextension

import com.google.gson.annotations.SerializedName

data class ApiStreamResponse(
    val data: List<StreamQuality>
)

data class StreamQuality(
    @SerializedName(value="360", alternate=["480", "720","1080"])
    val stream: StreamResult
)

data class StreamResult(
    val id: Int,
    val filesize: Int,
    val crc32: String,
    val revision: String,
    val fansub: String,
    val audio: String,
    val disc: String,
    val hq: Int,
    val kwik: String,
)