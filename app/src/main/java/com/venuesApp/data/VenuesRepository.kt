package com.venuesApp.data

import com.venuesApp.data.model.Venue
import com.venuesApp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface VenuesRepository {

    fun getVenues(
        client_id: String,
        client_secret: String,
        version: String,
        longLat: String,
        radius: Int,
        limit: Int
    ) : Flow<Resource<List<Venue>>>

}