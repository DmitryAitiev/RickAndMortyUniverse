package com.example.rickandmortyuniverse.domain.entity

data class Episode(
    val id: Int,
    val name: String,
    val date: String,
    val episodeNumber: String,
    val character: List<String>
)