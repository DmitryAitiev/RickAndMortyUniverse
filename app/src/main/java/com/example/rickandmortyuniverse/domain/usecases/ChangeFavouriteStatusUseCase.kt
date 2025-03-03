package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStatusUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    suspend fun addToFavourite(episode: Episode) = repository.addToFavourite(episode)
    suspend fun removeFromFavourite(episodeId: Int) = repository.removeFromFavourite(episodeId)
}