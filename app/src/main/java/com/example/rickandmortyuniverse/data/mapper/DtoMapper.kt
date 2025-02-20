package com.example.rickandmortyuniverse.data.mapper

import com.example.rickandmortyuniverse.data.model.CharacterDto
import com.example.rickandmortyuniverse.data.model.EpisodesInfoResponseDto
import com.example.rickandmortyuniverse.data.model.EpisodesListResponseDto
import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import javax.inject.Inject

class DtoMapper @Inject constructor() {

    fun mapResponseToEpisode(responseDto: EpisodesInfoResponseDto): List<Episode> {
        val result = mutableListOf<Episode>()
        val episodes = responseDto.listOfEpisodes

        for (ep in episodes) {
            val episode = Episode(
                id = ep.id,
                name = ep.name,
                date = ep.date,
                episodeNumber = ep.episodeNumber,
                character = ep.characters
            )
            result.add(episode)
        }
        return result
    }

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