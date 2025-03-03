package com.example.rickandmortyuniverse.data.repository

import com.example.rickandmortyuniverse.data.local.db.FavouriteEpisodesDao
import com.example.rickandmortyuniverse.data.mapper.toDbModel
import com.example.rickandmortyuniverse.data.mapper.toEntities
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.domain.repository.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val favouriteEpisodesDao: FavouriteEpisodesDao
) : FavouriteRepository {
    override val favouriteEpisodes: Flow<List<Episode>> = favouriteEpisodesDao.getFavouriteEpisodes()
        .map { it.toEntities() }

    override fun observeIsFavourite(episodeId: Int): Flow<Boolean> = favouriteEpisodesDao
        .observeIsFavourite(episodeId)

    override suspend fun addToFavourite(episode: Episode) {
        favouriteEpisodesDao.addToFavourite(episode.toDbModel())
    }

    override suspend fun removeFromFavourite(episodeId: Int) {
        favouriteEpisodesDao.removeFromFavourite(episodeId)

    }
}