package com.example.rickandmortyuniverse.presentation.characters

import androidx.lifecycle.ViewModel
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.data.repository.RepositoryProvider
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.usecases.GetEpisodeById
import com.example.rickandmortyuniverse.domain.usecases.GetListCharactersUseCase
import kotlinx.coroutines.flow.map

class CharactersScreenViewModel(
    val episodeId: Int,
    private val repository: EpisodesListRepository = RepositoryProvider.episodeRepository
): ViewModel() {

    private val getListCharactersUseCase = GetListCharactersUseCase(repository)
    private val getEpisodeById = GetEpisodeById(repository)

    val episode = getEpisodeById(episodeId)

    val screenState = getListCharactersUseCase(episode)
        .map {
            CharacterScreenState.CharactersLoaded(
                episode = episode,
                characters = it
            )
        }
}