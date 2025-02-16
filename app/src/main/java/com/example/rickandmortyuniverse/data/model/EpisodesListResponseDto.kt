package com.example.rickandmortyuniverse.data.model

import com.google.gson.annotations.SerializedName

data class EpisodesListResponseDto(
    @SerializedName("results") val listOfEpisodes: List<EpisodeDto>
)
