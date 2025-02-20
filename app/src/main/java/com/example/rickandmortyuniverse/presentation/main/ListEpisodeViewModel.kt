package com.example.rickandmortyuniverse.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.data.repository.RepositoryProvider
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.usecases.GetEpisodesFlowUseCase
import com.example.rickandmortyuniverse.domain.usecases.GetListEpisodesUseCase
import com.example.rickandmortyuniverse.domain.usecases.LoadNextDataUseCase
import com.example.rickandmortyuniverse.extensions.mergeWith
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListEpisodeViewModel(
    private val repository: EpisodesListRepository = RepositoryProvider.episodeRepository
): ViewModel() {

    private val getListEpisodesUseCase = GetListEpisodesUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val getEpisodeFlowUseCase = GetEpisodesFlowUseCase(repository)

    private val episodesFlow = getListEpisodesUseCase()

    private val loadNextDataFlow = MutableSharedFlow<ListEpisodesScreenState>()

    val episodesPagingFlow = getEpisodeFlowUseCase()
        .cachedIn(viewModelScope)

    val screenState = episodesFlow
        .filter { it.isNotEmpty() }
        .map { ListEpisodesScreenState.EpisodesState(episodes = it) as ListEpisodesScreenState }
        .onStart { emit(ListEpisodesScreenState.Loading) }
        .mergeWith(loadNextDataFlow)

    fun loadNextEpisodes() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                ListEpisodesScreenState.EpisodesState(
                    episodes = episodesFlow.value,
                    nextDataIsLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }
}