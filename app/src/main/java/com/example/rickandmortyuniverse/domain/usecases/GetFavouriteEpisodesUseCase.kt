package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouriteEpisodesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke() = repository.favouriteEpisodes
}