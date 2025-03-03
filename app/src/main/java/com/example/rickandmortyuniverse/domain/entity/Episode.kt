package com.example.rickandmortyuniverse.domain.entity

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class Episode(
    val id: Int,
    val name: String,
    val date: String,
    val episodeNumber: String,
    val character: ImmutableList<String> = persistentListOf(),
    val isFavourite: Boolean = false
)