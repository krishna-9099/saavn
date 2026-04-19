package com.krishnatune.data.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.krishnatune.api.SaavnApi
import com.krishnatune.db.daos.SpeedDialDao
import com.krishnatune.models.HomeSectionItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HomeRepository(
    private val api: SaavnApi = SaavnApi.create(),
    private val speedDialDao: SpeedDialDao? = null,
) {
    suspend fun getLaunchData() = api.getLaunchData()

    fun observePinnedItems(): Flow<List<HomeSectionItem>> {
        val dao = speedDialDao ?: return flowOf(emptyList())
        return dao.observeAll().map { entities ->
            entities.map { entity -> entity.toHomeSectionItem() }
        }
    }

    fun observePinnedIds(): Flow<Set<String>> {
        val dao = speedDialDao ?: return flowOf(emptySet())
        return dao.observeAllIds().map { ids -> ids.toSet() }
    }

    suspend fun toggleSpeedDial(item: HomeSectionItem) {
        val dao = speedDialDao ?: return
        val speedDialItem = item.toSpeedDialItemEntityOrNull() ?: return

        withContext(Dispatchers.IO) {
            val isPinned = dao.isPinned(speedDialItem.id).first()
            if (isPinned) {
                dao.deleteById(speedDialItem.id)
            } else {
                dao.insert(speedDialItem)
            }
        }
    }

    fun buildSpeedDialItems(
        pinnedItems: List<HomeSectionItem>,
        candidateItems: List<HomeSectionItem>,
        maxItems: Int = 27,
    ): List<HomeSectionItem> {
        if (maxItems <= 0) {
            return emptyList()
        }

        val merged = mutableListOf<HomeSectionItem>()
        val seenKeys = linkedSetOf<String>()

        fun addUnique(item: HomeSectionItem) {
            val key = item.speedDialKey()
            if (seenKeys.add(key)) {
                merged.add(item)
            }
        }

        pinnedItems.forEach(::addUnique)
        candidateItems.forEach(::addUnique)

        return merged.take(maxItems)
    }

    fun getSectionPagingData(
        source: String?,
        initialItems: List<HomeSectionItem>
    ): Flow<PagingData<HomeSectionItem>> {
        if (initialItems.isEmpty()) {
            return flowOf(PagingData.empty())
        }

        return when (source) {
            "top_playlists",
            "new_trending",
            "new_albums",
            "charts" -> {
                Pager(
                    config = PagingConfig(
                        pageSize = initialItems.size.coerceAtLeast(1),
                        enablePlaceholders = false
                    )
                ) {
                    HomeSectionPagingSource(
                        source = source,
                        api = api,
                        initialItems = initialItems
                    )
                }.flow
            }

            else -> flowOf(PagingData.from(initialItems))
        }
    }
}

private fun HomeSectionItem.speedDialKey(): String {
    return id?.takeIf { it.isNotBlank() }
        ?: perma_url?.takeIf { it.isNotBlank() }
        ?: listOf(type.orEmpty(), title.orEmpty(), subtitle.orEmpty()).joinToString("|")
}

data class PagedHomeSectionResponse(
    @SerializedName("data") val data: List<HomeSectionItem>? = null,
    @SerializedName("count") val count: Int? = null,
    @SerializedName("last_page") val lastPage: Boolean? = null
)

data class FeaturedPlaylistResponse(
    @SerializedName("data") val data: List<FeaturedPlaylistItem>? = null,
    @SerializedName("count") val count: Int? = null,
    @SerializedName("last_page") val lastPage: Boolean? = null
)

data class FeaturedPlaylistItem(
    @SerializedName("listid") val listId: String? = null,
    @SerializedName("listname") val listName: String? = null,
    @SerializedName("secondary_subtitle") val secondarySubtitle: String? = null,
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("data_type") val dataType: String? = null,
    @SerializedName("count") val count: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("perma_url") val permaUrl: String? = null,
)

fun FeaturedPlaylistItem.toHomeSectionItem(): HomeSectionItem {
    return HomeSectionItem(
        id = listId,
        title = listName,
        subtitle = secondarySubtitle ?: firstName,
        type = dataType ?: "playlist",
        image = image,
        perma_url = permaUrl,
        language = null,
        more_info = buildMap {
            count?.let { put("song_count", it.toString()) }
            firstName?.let { put("firstname", it) }
        }
    )
}
