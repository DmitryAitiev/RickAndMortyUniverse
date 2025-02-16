package com.example.rickandmortyuniverse.presentation.characters

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode

sealed class CharacterScreenState {

    data object Initial: CharacterScreenState()
    data object Loading: CharacterScreenState()
    data class CharactersLoaded(val episode: Episode, val characters: List<Character>): CharacterScreenState()
}