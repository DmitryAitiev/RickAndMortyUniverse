package com.example.rickandmortyuniverse.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Episode(
    val id: Int,
    val name: String,
    val date: String,
    val episodeNumber: String,
    val character: List<String>
)