package com.example.rickandmortyuniverse.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.data.repository.RepositoryProvider
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.usecases.GetEpisodesFlowUseCase
import com.example.rickandmortyuniverse.domain.usecases.GetListEpisodesUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListEpisodeViewModel(
    private val repository: EpisodesListRepository = RepositoryProvider.episodeRepository
): ViewModel() {

    private val getEpisodeFlowUseCase = GetEpisodesFlowUseCase(repository)
    private val getListEpisodesUseCase = GetListEpisodesUseCase(repository)

    val episodesPagingFlow = getEpisodeFlowUseCase()
        .cachedIn(viewModelScope)

    val screenState = getListEpisodesUseCase()
        .map { ListEpisodesScreenState.EpisodesState as ListEpisodesScreenState }
        .onStart { emit(ListEpisodesScreenState.Loading) }
}