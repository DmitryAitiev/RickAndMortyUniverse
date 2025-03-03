package com.example.rickandmortyuniverse.di

import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.data.repository.FavouriteRepositoryImpl
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.repository.FavouriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinds {

    @Binds
    @Singleton
    fun bindRepository(repositoryImpl: EpisodesListRepositoryImpl): EpisodesListRepository

    @Binds
    @Singleton
    fun bindFavouriteRepository(repositoryImpl: FavouriteRepositoryImpl): FavouriteRepository
}