package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository

class LoadNextDataUseCase(
    private val repository: EpisodesListRepository
) {

    suspend operator fun invoke() {
        return repository.loadNextData()
    }
}