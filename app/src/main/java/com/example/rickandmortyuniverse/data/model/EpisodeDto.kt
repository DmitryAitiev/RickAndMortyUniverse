package com.example.rickandmortyuniverse.data.model

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val date: String,
    @SerializedName("episode") val episodeNumber: String,
    @SerializedName("characters") val characters: List<String>
)
