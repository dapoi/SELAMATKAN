package com.dafdev.selamatkan.data.source

import com.dafdev.selamatkan.data.source.remote.network.StatusResponseOnline
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is StatusResponseOnline.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map {
                    Resource.Success(it)
                })
            }

            is StatusResponseOnline.Error -> {
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }
    }


    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<StatusResponseOnline<RequestType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}