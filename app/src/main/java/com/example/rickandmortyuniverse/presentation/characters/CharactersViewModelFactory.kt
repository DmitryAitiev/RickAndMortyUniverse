package com.example.rickandmortyuniverse.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyuniverse.domain.entity.Episode

class CharactersViewModelFactory(
    private val episodeId: Int
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharactersScreenViewModel(episodeId) as T
    }
}