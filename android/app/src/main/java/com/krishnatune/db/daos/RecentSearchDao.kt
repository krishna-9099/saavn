package com.krishnatune.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krishnatune.db.entities.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM recent_search ORDER BY searchedAt DESC LIMIT :limit")
    fun observeRecentSearches(limit: Int = 10): Flow<List<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: RecentSearchEntity)

    @Query("DELETE FROM recent_search WHERE query = :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM recent_search")
    suspend fun clearAll()
}
