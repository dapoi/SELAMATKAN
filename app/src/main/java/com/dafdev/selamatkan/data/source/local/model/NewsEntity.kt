package com.dafdev.selamatkan.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey()
    val title: String,
    val publishedAt: String,
    val urlToImage: String,
    val url: String,
    val content: String,
)