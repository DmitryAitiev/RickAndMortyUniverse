package com.example.rickandmortyuniverse.domain.repository

import com.example.rickandmortyuniverse.domain.entity.Episode
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteEpisodes: Flow<List<Episode>>

    fun observeIsFavourite(episodeId: Int): Flow<Boolean>

    suspend fun addToFavourite(episode: Episode)

    suspend fun removeFromFavourite(episodeId: Int)
}