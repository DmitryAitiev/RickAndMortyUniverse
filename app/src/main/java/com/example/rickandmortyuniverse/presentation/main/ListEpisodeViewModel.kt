package com.example.rickandmortyuniverse.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortyuniverse.data.repository.EpisodesListRepositoryImpl
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.domain.usecases.GetEpisodesFlowUseCase
import com.example.rickandmortyuniverse.domain.usecases.GetListEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEpisodeViewModel @Inject constructor(
    private val getEpisodeFlowUseCase: GetEpisodesFlowUseCase,
    private val getListEpisodesUseCase: GetListEpisodesUseCase
): ViewModel() {

    val episodesPagingFlow = getEpisodeFlowUseCase()
        .cachedIn(viewModelScope)

    val screenState = getListEpisodesUseCase()
        .map { ListEpisodesScreenState.EpisodesState as ListEpisodesScreenState }
        .onStart { emit(ListEpisodesScreenState.Loading)
        }
}