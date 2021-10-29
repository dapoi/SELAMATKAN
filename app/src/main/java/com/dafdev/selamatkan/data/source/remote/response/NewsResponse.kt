package com.dafdev.selamatkan.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Articles>,

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Int
)

data class Articles(
    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("urlToImage")
    val urlToImage: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("content")
    val content: String
)