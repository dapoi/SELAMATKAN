package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.BuildConfig
import com.dafdev.selamatkan.data.source.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {
    /**
     * Api Berita
     */
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "id",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY_NEWS
    ): NewsResponse
}