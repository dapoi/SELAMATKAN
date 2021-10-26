package com.dafdev.selamatkan.data.source

import com.dafdev.selamatkan.data.source.remote.network.ApiResponseOnline
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponseOnline.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is ApiResponseOnline.Error -> {
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }
    }


    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponseOnline<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}