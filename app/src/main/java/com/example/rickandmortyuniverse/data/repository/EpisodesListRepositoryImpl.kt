package com.example.rickandmortyuniverse.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortyuniverse.data.mapper.DtoMapper
import com.example.rickandmortyuniverse.data.model.EpisodesInfoResponseDto
import com.example.rickandmortyuniverse.data.network.ApiFactory
import com.example.rickandmortyuniverse.data.network.ApiService
import com.example.rickandmortyuniverse.data.paging.EpisodePagingSource
import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.EpisodesListRepository
import com.example.rickandmortyuniverse.extensions.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class EpisodesListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: DtoMapper
): EpisodesListRepository {

    private val apiService: ApiService = ApiFactory.apiService
    private val mapper = DtoMapper()

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val _episodeCache = MutableStateFlow<List<Episode>>(emptyList())
    val episodeCache: StateFlow<List<Episode>> = _episodeCache

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
        return episodeCache.value.firstOrNull { it.id == episodeId } ?:
            throw NullPointerException("Hyeta s polycheniem id ne raboteaet")
    }

    override fun getEpisodesFlow(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = 11,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EpisodePagingSource(apiService, mapper) { newEpisodes ->
                    _episodeCache.value += newEpisodes
                }
            }
        ).flow
    }

    override fun getListEpisodes(): StateFlow<List<Episode>> = episodeCache

    companion object {
        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}