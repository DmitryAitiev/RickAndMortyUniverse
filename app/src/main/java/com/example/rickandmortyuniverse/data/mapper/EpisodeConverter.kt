package com.example.rickandmortyuniverse.data.mapper

import com.example.rickandmortyuniverse.data.model.EpisodesInfoResponseDto
import com.example.rickandmortyuniverse.domain.entity.Episode
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class EpisodeConverter @Inject constructor() {

    fun mapResponseToEpisode(responseDto: EpisodesInfoResponseDto): List<Episode> {
        val result = mutableListOf<Episode>()
        val episodes = responseDto.listOfEpisodes

        for (ep in episodes) {
            val episode = Episode(
                id = ep.id,
                name = ep.name,
                date = ep.date,
                episodeNumber = ep.episodeNumber,
                character = ep.characters.toImmutableList()
            )
            result.add(episode)
        }
        return result
    }
}