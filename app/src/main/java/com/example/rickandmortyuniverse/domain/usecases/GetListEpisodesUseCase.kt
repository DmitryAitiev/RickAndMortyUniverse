package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetListEpisodesUseCase @Inject constructor(
    private val repository: EpisodesListRepository
) {

    operator fun invoke(): StateFlow<List<Episode>> {
        return repository.getListEpisodes()
    }
}