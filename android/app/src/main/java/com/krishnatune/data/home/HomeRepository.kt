package com.krishnatune.data.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.annotations.SerializedName
import com.krishnatune.api.SaavnApi
import com.krishnatune.models.HomeSectionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeRepository(
    private val api: SaavnApi = SaavnApi.create()
) {
    suspend fun getLaunchData() = api.getLaunchData()

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
