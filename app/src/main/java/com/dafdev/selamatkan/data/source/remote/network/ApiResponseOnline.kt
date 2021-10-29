package com.dafdev.selamatkan.data.source.remote.network

sealed class ApiResponseOnline<out T> {
    data class Success<out T>(val data: T) : ApiResponseOnline<T>()
    data class Error(val errorMessage: String?) : ApiResponseOnline<Nothing>()
}