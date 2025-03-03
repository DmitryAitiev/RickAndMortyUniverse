package com.example.rickandmortyuniverse.data.mapper

import com.example.rickandmortyuniverse.data.local.model.EpisodeDbModel
import com.example.rickandmortyuniverse.domain.entity.Episode
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf

fun Episode.toDbModel(): EpisodeDbModel = EpisodeDbModel(id, name, date, episodeNumber)

fun EpisodeDbModel.toEntity(): Episode = Episode(id, name, date, episodeNumber, character = persistentListOf())

fun List<EpisodeDbModel>.toEntities(): List<Episode> = map {it.toEntity()}

fun Episode.getSeasonNumber(): Int {
    return this.episodeNumber.substring(1, 3).toInt()
}