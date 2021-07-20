package com.venuesApp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.venuesApp.data.db.LocalDatabase
import com.venuesApp.data.db.VenuesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun database(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, LocalDatabase::class.java,"VenuesApp_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideVenuesDao(
        localDatabase:LocalDatabase
    ) :VenuesDao = localDatabase.venuesDao()
}