package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetEpisodeById @Inject constructor(
    private val repository: EpisodesListRepository
) {
    operator fun invoke(episodeId: Int): Episode {
        return repository.getEpisode(episodeId)
    }
}