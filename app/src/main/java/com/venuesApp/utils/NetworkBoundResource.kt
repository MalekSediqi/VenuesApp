package com.currencyConverterApp.utils

import com.venuesApp.utils.Resource
import com.venuesApp.utils.networkStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { networkStatus }
) = channelFlow {
    val data = query().first()
    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(Resource.loading(it)) }
        }

        try {
            delay(2000)
            saveFetchResult(fetch())
            loading.cancel()
            query().collect { send(Resource.success(it)) }
        } catch (t: Throwable) {
            loading.cancel()
            query().collect { send(Resource.error(t.message.toString(), null)) }
        }
    } else {
        query().collect { send(Resource.success(it)) }
    }
}