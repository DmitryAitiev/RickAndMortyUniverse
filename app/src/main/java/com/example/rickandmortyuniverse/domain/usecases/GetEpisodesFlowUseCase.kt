package com.example.rickandmortyuniverse.domain.usecases

import androidx.paging.PagingData
import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetEpisodesFlowUseCase(
    private val repository: EpisodesListRepository
) {
    operator fun invoke(): Flow<PagingData<Episode>> {
        return repository.getEpisodesFlow()
    }
}