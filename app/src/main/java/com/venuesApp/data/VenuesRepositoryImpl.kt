package com.venuesApp.data

import com.currencyConverterApp.utils.networkBoundResource
import com.venuesApp.data.db.VenuesDao
import com.venuesApp.data.model.Venue
import com.venuesApp.data.net.VenuesAPI
import com.venuesApp.utils.Resource
import com.venuesApp.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VenuesRepositoryImpl @Inject constructor(
    private val venuesAPI: VenuesAPI,
    private val venuesDao: VenuesDao) : VenuesRepository {

    @ExperimentalCoroutinesApi
    override fun getVenues(
        client_id: String,
        client_secret: String,
        version: String,
        longLat: String,
        radius: Int,
        limit: Int
    ): Flow<Resource<List<Venue>>> {
        return networkBoundResource(

            fetchFromLocal = { venuesDao.getVenues() },
            shouldFetchFromRemote = { true },
            fetchFromRemote = {
                venuesAPI.getVenues(client_id,client_secret,version,longLat,radius,limit)

            },
            saveRemoteData = {
                    venuesDao.insertVenues(it)
            },
            onFetchFailed = { errorBody, statusCode -> "$errorBody + $statusCode" }
        ).map {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
                Resource.Status.SUCCESS -> {
                    val venues = it.data
                    Resource.success(venues)
                }
                Resource.Status.ERROR -> {
                    Resource.error(it.message!!, null)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}