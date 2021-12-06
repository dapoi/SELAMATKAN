package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.BuildConfig
import com.dafdev.selamatkan.data.source.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {
    /**
     * Api Berita
     */
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") countryCode: String = "id",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }
}