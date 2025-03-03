package com.example.rickandmortyuniverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyuniverse.data.mapper.EpisodeConverter
import com.example.rickandmortyuniverse.data.network.ApiService
import com.example.rickandmortyuniverse.domain.entity.Episode

class EpisodePagingSource(
    private val apiService: ApiService,
    private val episodeConverter: EpisodeConverter,
    private val onEpisodeLoaded: (List<Episode>) -> Unit
): PagingSource<Int, Episode>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val page = params.key ?: 1
        return try {
            val response = apiService.loadEpisodes(page)
            val episodes = episodeConverter.mapResponseToEpisode(response)
            onEpisodeLoaded(episodes)

            val nextPage = response.episodesInfoDto.nextPage
                ?.substringAfter("page=")
                ?.toIntOrNull()
            LoadResult.Page(
                data = episodes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}