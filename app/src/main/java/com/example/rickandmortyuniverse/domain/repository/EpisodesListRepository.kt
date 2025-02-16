package com.example.rickandmortyuniverse.domain.repository

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import kotlinx.coroutines.flow.StateFlow

interface EpisodesListRepository {

    fun getListEpisodes(): StateFlow<List<Episode>>

    fun getListCharacters(episode: Episode): StateFlow<List<Character>>

    fun getEpisode(episodeId: Int): Episode

    suspend fun loadNextData()
}