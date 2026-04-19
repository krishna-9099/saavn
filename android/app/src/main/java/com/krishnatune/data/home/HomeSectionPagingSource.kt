package com.krishnatune.data.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.krishnatune.api.SaavnApi
import com.krishnatune.models.HomeSectionItem

class HomeSectionPagingSource(
    private val source: String,
    private val api: SaavnApi,
    private val initialItems: List<HomeSectionItem>
) : PagingSource<Int, HomeSectionItem>() {

    private val pageSize = initialItems.size.coerceAtLeast(1)

    override fun getRefreshKey(state: PagingState<Int, HomeSectionItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeSectionItem> {
        val page = params.key ?: 1

        return try {
            val items = when (page) {
                1 -> initialItems
                else -> fetchPage(page)
            }

            val nextKey = if (items.isEmpty()) null else page + 1

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun fetchPage(page: Int): List<HomeSectionItem> {
        return when (source) {
            "top_playlists" -> {
                api.getFeaturedPlaylists(
                    page = page,
                    count = pageSize
                ).data
                    ?.map { it.toHomeSectionItem() }
                    .orEmpty()
                    .dedupeAgainst(initialItems)
            }

            "new_trending" -> {
                fetchGrowingWindow(page) { count ->
                    api.getTrending(count = count).dedupeAgainst(initialItems)
                }
            }

            "new_albums" -> {
                fetchGrowingWindow(page) { count ->
                    api.getAlbums(count = count).data.orEmpty().dedupeAgainst(initialItems)
                }
            }

            "charts" -> {
                fetchGrowingWindow(page) { count ->
                    api.getCharts(count = count).dedupeAgainst(initialItems)
                }
            }

            else -> emptyList()
        }
    }

    private suspend fun fetchGrowingWindow(
        page: Int,
        request: suspend (count: Int) -> List<HomeSectionItem>
    ): List<HomeSectionItem> {
        val targetCount = initialItems.size + ((page - 1) * pageSize)
        val expandedItems = request(targetCount)
        val alreadyLoaded = initialItems.size + ((page - 2) * pageSize)
        return expandedItems.drop(alreadyLoaded)
    }
}

private fun List<HomeSectionItem>.dedupeAgainst(seedItems: List<HomeSectionItem>): List<HomeSectionItem> {
    if (isEmpty()) return this

    val existingIds = seedItems.mapNotNull { it.id }.toHashSet()
    return filter { item ->
        val id = item.id
        id == null || id !in existingIds
    }
}
