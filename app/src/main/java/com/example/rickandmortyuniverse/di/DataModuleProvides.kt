package com.example.rickandmortyuniverse.di

import android.content.Context
import androidx.room.Room
import com.example.rickandmortyuniverse.data.local.db.FavouriteDatabase
import com.example.rickandmortyuniverse.data.local.db.FavouriteEpisodesDao
import com.example.rickandmortyuniverse.data.network.ApiFactory
import com.example.rickandmortyuniverse.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProvides {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavouriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavouriteDatabase::class.java,
            "favourite_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(database: FavouriteDatabase): FavouriteEpisodesDao {
        return database.favouriteEpisodesDao()
    }
}