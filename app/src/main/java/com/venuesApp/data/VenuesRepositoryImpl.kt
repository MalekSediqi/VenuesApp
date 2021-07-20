package com.venuesApp.data

import com.venuesApp.data.db.VenuesDao
import com.venuesApp.data.model.Venue
import com.venuesApp.data.net.VenuesAPI
import com.venuesApp.utils.Resource
import com.venuesApp.utils.networkBoundResource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
        networkBoundResource(
            fetchFromLocal = { venuesDao.getVenues() },
            shouldFetchFromRemote = { false },
            fetchFromRemote = { venuesAPI.getVenues(client_id,client_secret,version,longLat,radius,limit) }
        )
    }
}