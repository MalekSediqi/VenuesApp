package com.venuesApp.data.net

import com.venuesApp.data.model.Venue
import com.venuesApp.data.model.VenueWithDetails
import com.venuesApp.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenuesAPI {

    @GET("search")
    fun getVenues(@Query("client_id") client_id: String,
                          @Query("client_secret") client_secret:String,
                          @Query("v") version:String,
                          @Query("ll") location:String,
                          @Query("radius") radius:Int,
                          @Query("limit") limit: Int ): Flow<ApiResponse<List<Venue>>>

    @GET("{VENUE_ID}")
    fun getVenuesDetails(
        @Path("VENUE_ID") venue_id:String,
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret:String,
        @Query("v") version:String
    ) : Flow<ApiResponse<VenueWithDetails>>

}