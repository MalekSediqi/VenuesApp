package com.venuesApp.utils

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
inline fun <DB, REMOTE> networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<DB>,
    crossinline shouldFetchFromRemote: (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: () -> Flow<ApiResponse<REMOTE>>,
    crossinline saveRemoteData: (REMOTE) -> Unit = { },
    crossinline onFetchFailed: (errorBody: String?, statusCode: Int) -> Unit = { _: String?, _: Int -> }
) = flow<Resource<DB>> {

    emit(Resource.loading(null))

    val localData = fetchFromLocal().first()

    if (shouldFetchFromRemote(localData)) {

        emit(Resource.loading(localData))

        fetchFromRemote().collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    apiResponse.body?.let { saveRemoteData(it) }
                    emitAll(fetchFromLocal().map { dbData ->
                        Resource.success(dbData)
                    })
                }
                is ApiErrorResponse -> {
                    onFetchFailed(apiResponse.errorMessage, apiResponse.statusCode)
                    emitAll(fetchFromLocal().map {
                        Resource.error(
                            apiResponse.errorMessage,
                            it
                        )
                    })
                }
                is ApiEmptyResponse -> {
                    emitAll(fetchFromLocal().map { dbData ->
                        Resource.success(dbData)
                    })
                }
            }
        }
    } else {
        emitAll(fetchFromLocal().map { Resource.success(it) })
    }
}
