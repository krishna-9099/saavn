package com.krishnatune.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krishnatune.db.entities.SpeedDialItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeedDialDao {
    @Query("SELECT * FROM speed_dial_item ORDER BY createdAt ASC")
    fun observeAll(): Flow<List<SpeedDialItemEntity>>

    @Query("SELECT id FROM speed_dial_item")
    fun observeAllIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SpeedDialItemEntity)

    @Query("DELETE FROM speed_dial_item WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM speed_dial_item WHERE id = :id)")
    fun isPinned(id: String): Flow<Boolean>
}
