package com.example.helloworld.retrofit

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
data class GistFile(
    @SerializedName("filename") val filename: String,
    @SerializedName("type") val type: String,
    @SerializedName("language") val language: String?,
    @SerializedName("size") val size: Int,
    @SerializedName("raw_url") val rawUrl: String,
    @SerializedName("content") val content: String?
)
data class Gist (
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("description") val description: String?,
    @SerializedName("public") val isPublic: Boolean,
    @SerializedName("files") val files: Map<String, GistFile>
)

