package com.venuesApp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.venuesApp.data.net.model.test

@Database(
    entities = [
        test::class
   ], version = 1
)

abstract class LocalDatabase : RoomDatabase() {

    abstract fun testDao() : testDao
}