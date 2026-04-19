package com.krishnatune.data.search

import com.krishnatune.api.SaavnApi
import com.krishnatune.db.daos.RecentSearchDao
import com.krishnatune.db.entities.RecentSearchEntity
import com.krishnatune.models.SearchUiItem
import com.krishnatune.models.toUiItem
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val api: SaavnApi = SaavnApi.create(),
    private val recentSearchDao: RecentSearchDao
) {
    suspend fun search(query: String, limit: Int = 24): List<SearchUiItem> {
        return api.searchAll(
            query = query.trim(),
            count = limit
        ).results
            .orEmpty()
            .mapNotNull { it.toUiItem() }
    }

    fun observeRecentSearches(limit: Int = 10): Flow<List<RecentSearchEntity>> {
        return recentSearchDao.observeRecentSearches(limit)
    }

    suspend fun storeRecentSearch(query: String, type: String = "search") {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isBlank()) return

        recentSearchDao.upsert(
            RecentSearchEntity(
                query = normalizedQuery,
                type = type,
                searchedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun removeRecentSearch(query: String) {
        recentSearchDao.deleteByQuery(query)
    }

    suspend fun clearRecentSearches() {
        recentSearchDao.clearAll()
    }
}
