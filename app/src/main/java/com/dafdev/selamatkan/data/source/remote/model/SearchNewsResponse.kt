package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class SearchNewsResponse(

    @Json(name = "totalResults")
    val totalResults: Int? = null,

    @Json(name = "articles")
    val articles: List<ArticlesItemSearch?>? = null,

    @Json(name = "status")
    val status: String? = null
)

data class Source(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: Any? = null
)

data class ArticlesItemSearch(

    @Json(name = "publishedAt")
    val publishedAt: String? = null,

    @Json(name = "author")
    val author: String? = null,

    @Json(name = "urlToImage")
    val urlToImage: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "source")
    val source: Source? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "url")
    val url: String? = null,

    @Json(name = "content")
    val content: String? = null
)
