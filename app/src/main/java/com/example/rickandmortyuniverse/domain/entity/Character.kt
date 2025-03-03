package com.example.rickandmortyuniverse.domain.entity

import androidx.compose.runtime.Immutable

data class Character(
    val id: Int,
    val name: String,
    val liveStatus: String,
    val image: String
)
