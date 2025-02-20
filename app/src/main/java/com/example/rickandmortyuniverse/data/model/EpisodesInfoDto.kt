package com.example.rickandmortyuniverse.data.model

import com.google.gson.annotations.SerializedName

data class EpisodesInfoDto(
    @SerializedName("count") val countEpisodes: Int,
    @SerializedName("pages") val countPages: Int,
    @SerializedName("next") val nextPage: String?
)
