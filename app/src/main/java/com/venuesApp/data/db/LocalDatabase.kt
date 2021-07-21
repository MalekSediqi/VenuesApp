package com.venuesApp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.venuesApp.data.model.Venue
import com.venuesApp.data.model.VenueWithDetails

@Database(
    entities = [
        Venue::class,
        VenueWithDetails::class
    ], version = 1, exportSchema = false
)

abstract class LocalDatabase : RoomDatabase() {

    abstract fun venuesDao(): VenuesDao
}