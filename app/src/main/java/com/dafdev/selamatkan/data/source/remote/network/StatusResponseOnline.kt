package com.dafdev.selamatkan.data.source.remote.network

sealed class StatusResponseOnline<out T> {
    data class Success<out T>(val data: T) : StatusResponseOnline<T>()
    data class Error(val errorMessage: String?) : StatusResponseOnline<Nothing>()
}