package com.example.rickandmortyuniverse.data.model

import com.google.gson.annotations.SerializedName

data class EpisodesInfoResponseDto(
    @SerializedName("info") val episodesInfoDto: EpisodesInfoDto,
    @SerializedName("results") val listOfEpisodes: List<EpisodeDto>
)