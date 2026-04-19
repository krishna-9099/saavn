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

    suspend fun getAutocomplete(query: String): List<SearchUiItem> {
        val response = api.getAutocomplete(query = query.trim())
        val items = mutableListOf<SearchUiItem>()

        response.topquery?.data?.take(2)?.mapNotNull { it.toUiItem() }?.let { items.addAll(it) }
        response.songs?.data?.take(3)?.mapNotNull { it.toUiItem() }?.let { items.addAll(it) }
        response.albums?.data?.take(2)?.mapNotNull { it.toUiItem() }?.let { items.addAll(it) }
        response.artists?.data?.take(2)?.mapNotNull { it.toUiItem() }?.let { items.addAll(it) }
        response.playlists?.data?.take(2)?.mapNotNull { it.toUiItem() }?.let { items.addAll(it) }

        return items
    }

    suspend fun searchByCategory(query: String, category: SearchCategory): List<SearchUiItem> {
        return when (category) {
            SearchCategory.ALL -> getAutocomplete(query)
            SearchCategory.SONGS -> api.searchSongs(query = query.trim()).results.orEmpty().mapNotNull { it.toUiItem() }
            SearchCategory.ALBUMS -> api.searchAlbums(query = query.trim()).results.orEmpty().mapNotNull { it.toUiItem() }
            SearchCategory.ARTISTS -> api.searchArtists(query = query.trim()).results.orEmpty().mapNotNull { it.toUiItem() }
            SearchCategory.PLAYLISTS -> api.searchPlaylists(query = query.trim()).results.orEmpty().mapNotNull { it.toUiItem() }
        }
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

enum class SearchCategory(val value: String) {
    ALL("all"),
    SONGS("songs"),
    ALBUMS("albums"),
    ARTISTS("artists"),
    PLAYLISTS("playlists")
}
