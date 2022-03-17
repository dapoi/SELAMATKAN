package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class NewsResponse(
    @Json(name = "articles")
    val articles: List<Articles?>?,

    @Json(name = "status")
    val status: String? = null,

    @Json(name = "totalResults")
    val totalResults: Int? = null
)

data class Articles(
    @Json(name = "author")
    val author: String? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "publishedAt")
    val publishedAt: String? = null,

    @Json(name = "urlToImage")
    val urlToImage: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "content")
    val content: String? = null
)