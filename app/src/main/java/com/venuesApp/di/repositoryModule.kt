package com.venuesApp.di

import com.venuesApp.data.VenuesRepository
import com.venuesApp.data.VenuesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class repositoryModule {
    @Binds
    @Singleton
    abstract fun provideVenuesRepositoryImpl(venuesRepositoryImpl: VenuesRepositoryImpl) : VenuesRepository
}