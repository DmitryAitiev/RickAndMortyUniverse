package com.example.rickandmortyuniverse.data.network

import com.example.rickandmortyuniverse.data.model.CharacterDto
import com.example.rickandmortyuniverse.data.model.EpisodesInfoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("episode")
    suspend fun loadEpisodes(
        @Query("page") page: Int
    ): EpisodesInfoResponseDto

    @GET("character/{ids}")
    suspend fun getCharacters(
        @Path("ids") ids: String
    ): List<CharacterDto>
}