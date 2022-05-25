package com.dafdev.selamatkan.data.source.remote.network

import com.dafdev.selamatkan.BuildConfig
import com.dafdev.selamatkan.data.source.remote.model.NewsResponse
import com.dafdev.selamatkan.data.source.remote.model.SearchNewsResponse
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

    @GET("v2/everything")
    suspend fun getNewsSearch(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 30,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("language") language: String = "id",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY_NEWS
    ): SearchNewsResponse
}