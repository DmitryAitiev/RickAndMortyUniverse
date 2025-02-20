package com.example.rickandmortyuniverse.domain.usecases

import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    private val repository: EpisodesListRepository
) {

    operator fun invoke(episode: Episode): StateFlow<List<Character>> {
        return repository.getListCharacters(episode)
    }
}