package com.venuesApp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.venuesApp.data.model.Venue

@Database(
    entities = [
        Venue::class
    ], version = 1
)

abstract class LocalDatabase : RoomDatabase() {

    abstract fun venuesDao(): VenuesDao
}