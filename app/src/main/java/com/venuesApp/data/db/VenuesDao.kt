package com.venuesApp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.venuesApp.data.model.Venue
import com.venuesApp.data.model.VenueWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface VenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenues(venues: List<Venue>)

    @Query("select * from Venue order by title")
    fun getVenues(): Flow<List<Venue>>

    @Query("select * from Venue Where title LIKE '%' || :title || '%'order by title")
    fun getVenueByTitle(title:String) : LiveData<List<Venue>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenueDetails(venueDetails: VenueWithDetails)

    @Query("select * from VenueWithDetails  Where id = :venueId")
    fun getVenueDetails(venueId:String): Flow<VenueWithDetails>

}