package com.venuesApp.data.net

import com.venuesApp.data.net.model.Venues
import retrofit2.http.GET
import retrofit2.http.Query

interface VenuesAPI {
    @GET("search")
    suspend fun getVenues(@Query("client_id") client_id: String,
                          @Query("client_secret") client_secret:String,
                          @Query("v") version:String,
                          @Query("ll") location:String,
                          @Query("radius") radius:Int,
                          @Query("limit") limit: Int ): Venues
}