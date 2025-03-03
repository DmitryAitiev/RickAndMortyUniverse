package com.example.rickandmortyuniverse.data.mapper

import com.example.rickandmortyuniverse.data.model.CharacterDto
import com.example.rickandmortyuniverse.domain.entity.Character
import javax.inject.Inject

class CharacterConverter @Inject constructor() {

    fun mapResponseToCharacter(responseDto: List<CharacterDto>): List<Character> {
        val result = mutableListOf<Character>()

        for (ch in responseDto) {
            val character = Character(
                id = ch.id,
                name = ch.name,
                liveStatus = ch.liveStatus,
                image = ch.image,
            )
            result.add(character)
        }
        return result
    }
}