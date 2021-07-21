package com.venuesApp.data

import androidx.lifecycle.LiveData
import com.currencyConverterApp.utils.networkBoundResource
import com.venuesApp.data.db.VenuesDao
import com.venuesApp.data.model.Venue
import com.venuesApp.data.model.VenueWithDetails
import com.venuesApp.data.net.VenuesAPI
import com.venuesApp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
            query = { venuesDao.getVenues() },
            fetch = { venuesAPI.getVenues(client_id,client_secret,version,longLat,radius,limit) },
            saveFetchResult = {
                venuesDao.insertVenues(it.response.venues)
            }
        )
    }

    override fun searchVenuesByTitle(title: String): LiveData<List<Venue>?> {
        return venuesDao.getVenueByTitle(title)
    }

    override fun getVenueWithDetails(
        venueId:String,
        client_id: String,
        client_secret: String,
        version: String
    ): Flow<Resource<VenueWithDetails>> {
        return networkBoundResource(
            query = { venuesDao.getVenueDetails(venueId) },
            fetch = { venuesAPI.getVenuesDetails(venueId,client_id,client_secret,version) },
            saveFetchResult = {
                venuesDao.insertVenueDetails(it)
            }
        )
    }
}