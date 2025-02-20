package com.example.rickandmortyuniverse.di

import android.content.Context
import com.example.rickandmortyuniverse.data.network.ApiFactory
import com.example.rickandmortyuniverse.data.network.ApiService
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule{

    @Singleton
    @Binds
    fun bindRepository(repositoryImpl: EpisodesListRepositoryImpl): EpisodesListRepository
    companion object {
        @Singleton
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}