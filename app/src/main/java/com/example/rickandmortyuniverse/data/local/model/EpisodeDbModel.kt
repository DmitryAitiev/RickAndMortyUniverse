package com.example.rickandmortyuniverse.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_episodes")
data class EpisodeDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val date: String,
    val episodeNumber: String,
)
