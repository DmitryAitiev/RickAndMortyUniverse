package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.repository.FavouriteRepository
import javax.inject.Inject

class ObserveFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {

    operator fun invoke(episodeId: Int) = repository.observeIsFavourite(episodeId)
}