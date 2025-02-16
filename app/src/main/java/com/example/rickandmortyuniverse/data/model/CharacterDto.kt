package com.example.rickandmortyuniverse.data.model

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val liveStatus: String,
    @SerializedName("image") val image: String
)
