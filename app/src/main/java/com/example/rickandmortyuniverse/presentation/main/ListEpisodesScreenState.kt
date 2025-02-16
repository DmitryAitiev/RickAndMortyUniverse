package com.example.rickandmortyuniverse.presentation.main

import com.example.rickandmortyuniverse.domain.entity.Episode

sealed class ListEpisodesScreenState {

    data object Initial: ListEpisodesScreenState()
    data object Loading: ListEpisodesScreenState()
    data class EpisodesState(
        val episodes: List<Episode>,
        val nextDataIsLoading: Boolean = false
    ): ListEpisodesScreenState()
}