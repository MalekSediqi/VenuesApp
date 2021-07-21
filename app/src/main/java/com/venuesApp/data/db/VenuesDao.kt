package com.venuesApp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.venuesApp.data.model.Venue
import kotlinx.coroutines.flow.Flow

@Dao
interface VenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVenues(venues: List<Venue>)

    @Query("select * from Venue order by title")
    fun getVenues(): Flow<List<Venue>>

}