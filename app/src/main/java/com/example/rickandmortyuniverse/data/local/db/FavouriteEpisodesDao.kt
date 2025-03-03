package com.example.rickandmortyuniverse.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyuniverse.data.local.model.EpisodeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteEpisodesDao {

    @Query("Select * from favourite_episodes")
    fun getFavouriteEpisodes(): Flow<List<EpisodeDbModel>>

    @Query("Select exists (select * from favourite_episodes where id=:episodeId Limit 1)")
    fun observeIsFavourite(episodeId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(episodeDbModel: EpisodeDbModel)

    @Query("delete from favourite_episodes where id=:episodeId")
    suspend fun removeFromFavourite(episodeId: Int)
}