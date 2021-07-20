package com.venuesApp.data

import com.venuesApp.data.db.VenuesDao
import com.venuesApp.data.model.Venue
import com.venuesApp.data.net.VenuesAPI
import com.venuesApp.utils.Resource
import com.venuesApp.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VenuesRepositoryImpl @Inject constructor(
    private val venuesAPI: VenuesAPI,
    private val venuesDao: VenuesDao) : VenuesRepository {

    override suspend fun saveVenues(venues: List<Venue>) {
        venuesDao.insertVenues(venues)
    }

    @InternalCoroutinesApi
    override suspend fun getVenues(
        client_id: String,
        client_secret: String,
        version: String,
        longLat: String,
        radius: Int,
        limit: Int
    ): Flow<Resource<List<Venue>>> {
        return networkBoundResource(
            fetchFromLocal = { venuesDao.getVenues() },
            shouldFetchFromRemote = { false },
            fetchFromRemote = { venuesAPI.getVenues(client_id,client_secret,version,longLat,radius,limit) },
            processRemoteResponse = {},
            saveRemoteData = {},
            onFetchFailed = { errorBody, statusCode -> "$errorBody + $statusCode" }
        ).map {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
                Resource.Status.SUCCESS -> {
                    val quote = it.data
                    Resource.success(quote)
                }
                Resource.Status.ERROR -> {
                    Resource.error(it.message!!, null)
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}