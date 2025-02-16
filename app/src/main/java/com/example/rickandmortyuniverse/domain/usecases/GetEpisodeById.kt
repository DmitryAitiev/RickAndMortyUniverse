package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import kotlinx.coroutines.flow.StateFlow

class GetEpisodeById(
    private val repository: EpisodesListRepository
) {
    operator fun invoke(episodeId: Int): Episode {
        return repository.getEpisode(episodeId)
    }
}