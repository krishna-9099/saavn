package com.krishnatune.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey val query: String,
    val type: String = "search",
    val searchedAt: Long = System.currentTimeMillis(),
)
