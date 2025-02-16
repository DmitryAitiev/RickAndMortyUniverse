package com.example.rickandmortyuniverse.data.repository

import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository

object RepositoryProvider {

    val episodeRepository: EpisodesListRepository by lazy {
        EpisodesListRepositoryImpl()
    }
}