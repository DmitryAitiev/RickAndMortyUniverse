package com.example.rickandmortyuniverse.domain.entity

data class Character(
    val id: Int,
    val name: String,
    val liveStatus: String,
    val image: String
)
