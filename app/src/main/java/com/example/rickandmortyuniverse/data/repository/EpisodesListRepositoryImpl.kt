package com.example.rickandmortyuniverse.data.repository

import android.util.Log
import com.example.rickandmortyuniverse.data.mapper.DtoMapper
import com.example.rickandmortyuniverse.data.model.EpisodesInfoResponseDto
import com.example.rickandmortyuniverse.data.network.ApiFactory
import com.example.rickandmortyuniverse.data.network.ApiService
import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.extensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class EpisodesListRepositoryImpl: EpisodesListRepository {

    private val apiService: ApiService = ApiFactory.apiService
    private val mapper = DtoMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<Episode>>()

    private val _episodes = mutableListOf<Episode>()
    private val episodes: List<Episode>
        get() = _episodes.toList()


    private var nextPage: Int? = 1

    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val page = nextPage
            if (page == null && episodes.isNotEmpty()) {
                emit(episodes)
                return@collect
            }
            val response = if (page != null)
                apiService.loadEpisodes(page)
            else null
            Log.d("TestNet", "nextPageInResponse: ${response?.episodesInfoDto?.nextPage}")
            Log.d("TestNet", "nextPageReal: ${nextPage}")
            Log.d("TestNet", "PageReal: ${page}")
            Log.d("TestNet", "nextResponse: ${response}")

            if (response != null) {
                nextPage = response.episodesInfoDto.nextPage.substringAfter("page=").toIntOrNull()
                val episodes = mapper.mapResponseToEpisode(response)
                _episodes.addAll(episodes)
                Log.d("TestNet", nextPage.toString())
                Log.d("TestNet", response.episodesInfoDto.nextPage)
                Log.d("TestNet", response.toString())

            }
            emit(episodes)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    override fun getListCharacters(episode: Episode): StateFlow<List<Character>> = flow {
        val idList = episode.character.map { url ->
            url.substringAfter("character/")
        }.joinToString(",")

        val characters = apiService.getCharacters(idList)
        emit(mapper.mapResponseToCharacter(characters))
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override fun getEpisode(episodeId: Int): Episode {
        Log.d("TestNET", episodes.toString())
        return episodes.firstOrNull { it.id == episodeId } ?:
            throw NullPointerException("Hyeta s polycheniem id ne raboteaet")
    }

    private val listEpisode: StateFlow<List<Episode>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = episodes
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override fun getListEpisodes(): StateFlow<List<Episode>> = listEpisode

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}