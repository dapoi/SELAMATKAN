package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class NewsResponse(

    @Json(name = "totalResults")
    val totalResults: Int? = null,

    @Json(name = "articles")
    val articles: List<ArticlesItem?>? = null,

    @Json(name = "status")
    val status: String? = null
)

data class ArticlesItem(

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "publishedAt")
    val publishedAt: String? = null,

    @Json(name = "urlToImage")
    val urlToImage: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "content")
    val content: String? = null
)
