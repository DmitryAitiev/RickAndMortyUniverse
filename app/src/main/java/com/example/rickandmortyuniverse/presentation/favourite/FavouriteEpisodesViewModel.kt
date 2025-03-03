package com.example.rickandmortyuniverse.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.usecases.ChangeFavouriteStatusUseCase
import com.example.rickandmortyuniverse.domain.usecases.GetFavouriteEpisodesUseCase
import com.example.rickandmortyuniverse.domain.usecases.ObserveFavouriteUseCase
import com.example.rickandmortyuniverse.presentation.characters.CharacterScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteEpisodesViewModel @Inject constructor(
    private val changeFavouriteStatusUseCase: ChangeFavouriteStatusUseCase,
    private val getFavouriteEpisodesUseCase: GetFavouriteEpisodesUseCase,
    private val observeFavouriteUseCase: ObserveFavouriteUseCase
): ViewModel() {

    val favouriteEpisodes = getFavouriteEpisodesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val screenState = getFavouriteEpisodesUseCase()
        .map {
            if (it.isNotEmpty())
            FavouriteScreenState.CharactersLoaded(
                episodes = it
            ) as FavouriteScreenState
            else {
                FavouriteScreenState.Empty as FavouriteScreenState
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FavouriteScreenState.Empty
        )


    fun changeFavouriteStatus(episode: Episode) {
        viewModelScope.launch {
            val isFavourite = observeFavouriteUseCase(episode.id).first()
            if(isFavourite) {
                changeFavouriteStatusUseCase.removeFromFavourite(episode.id)
            }
            else {
                changeFavouriteStatusUseCase.addToFavourite(episode)
            }
        }
    }
}