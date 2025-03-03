package com.example.rickandmortyuniverse.presentation.favourite

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode

sealed class FavouriteScreenState {

    data object Initial: FavouriteScreenState()
    data object Empty: FavouriteScreenState()
    data class CharactersLoaded(val episodes: List<Episode>): FavouriteScreenState()
}