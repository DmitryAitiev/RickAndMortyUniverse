package com.example.rickandmortyuniverse.presentation.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.usecases.GetEpisodeById
import com.example.rickandmortyuniverse.domain.usecases.GetListCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class CharactersScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getListCharactersUseCase: GetListCharactersUseCase,
    private val getEpisodeById: GetEpisodeById
): ViewModel() {

    private val episodeId: Int = checkNotNull(savedStateHandle["episodeId"]) {
        "EpisodeId is missing in SavedStateHandle"
    }

    val episode = getEpisodeById(episodeId)

    val screenState = getListCharactersUseCase(episode)
        .map {
            CharacterScreenState.CharactersLoaded(
                episode = episode,
                characters = it
            ) as CharacterScreenState
        }
        .onStart { emit(CharacterScreenState.Loading) }
}