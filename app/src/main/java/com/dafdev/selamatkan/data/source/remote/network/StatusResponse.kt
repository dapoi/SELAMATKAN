package com.dafdev.selamatkan.data.source.remote.network

sealed class StatusResponse<out R> {
    data class Success<out T>(val data: T) : StatusResponse<T>()
    data class Error(val errorMessage: String) : StatusResponse<Nothing>()
    object Empty : StatusResponse<Nothing>()
}