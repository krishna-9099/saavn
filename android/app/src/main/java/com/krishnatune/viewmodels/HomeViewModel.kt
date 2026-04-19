package com.krishnatune.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krishnatune.data.home.HomeRepository
import com.krishnatune.db.AppDatabase
import com.krishnatune.models.HomeDataResponse
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.models.ModuleConfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val modules: List<HomeModuleUi>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

data class HomeModuleUi(
    val key: String,
    val config: ModuleConfig,
    val initialItems: List<HomeSectionItem>,
    val pagedItems: Flow<PagingData<HomeSectionItem>>
)

class HomeViewModel(
    private val repository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val homeCandidates = MutableStateFlow<List<HomeSectionItem>>(emptyList())

    val pinnedSpeedDialItems: StateFlow<List<HomeSectionItem>> =
        repository.observePinnedItems()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val pinnedIds: StateFlow<Set<String>> =
        repository.observePinnedIds()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    val speedDialItems: StateFlow<List<HomeSectionItem>> =
        combine(pinnedSpeedDialItems, homeCandidates) { pinned, candidates ->
            repository.buildSpeedDialItems(
                pinnedItems = pinned,
                candidateItems = candidates,
                maxItems = 27,
            )
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchHomeData()
    }

    fun toggleSpeedDial(item: HomeSectionItem) {
        viewModelScope.launch {
            repository.toggleSpeedDial(item)
        }
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val response = repository.getLaunchData()
                homeCandidates.value = buildSpeedDialCandidates(response)

                val sortedModules = response.modules?.entries
                    ?.filter { it.value.position != null }
                    ?.sortedBy { it.value.position }
                    ?.mapNotNull { (key, config) ->
                        val items = getItemsForSource(config.source, response)
                            ?.filterNotNull()
                            .orEmpty()

                        if (items.isEmpty()) {
                            null
                        } else {
                            HomeModuleUi(
                                key = key,
                                config = config,
                                initialItems = items,
                                pagedItems = repository
                                    .getSectionPagingData(config.source, items)
                                    .cachedIn(viewModelScope)
                            )
                        }
                    }
                    .orEmpty()

                _uiState.value = HomeUiState.Success(sortedModules)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val database = AppDatabase.getInstance(application)
                    val repository = HomeRepository(speedDialDao = database.speedDialDao())
                    return HomeViewModel(repository) as T
                }
            }
        }
    }
}

private fun getItemsForSource(
    source: String?,
    data: HomeDataResponse
): List<HomeSectionItem>? {
    return when (source) {
        "new_trending" -> data.new_trending
        "top_playlists" -> data.top_playlists
        "new_albums" -> data.new_albums
        "browse_discover" -> data.browse_discover
        "charts" -> data.charts
        "radio" -> data.radio
        "artist_recos" -> data.artist_recos
        else -> null
    }
}

private fun buildSpeedDialCandidates(data: HomeDataResponse): List<HomeSectionItem> {
    val seedItems = buildList {
        addAll(data.new_trending.orEmpty())
        addAll(data.top_playlists.orEmpty())
        addAll(data.new_albums.orEmpty())
        addAll(data.browse_discover.orEmpty())
        addAll(data.charts.orEmpty())
        addAll(data.radio.orEmpty())
        addAll(data.artist_recos.orEmpty())
    }

    val seen = linkedSetOf<String>()
    val deduped = mutableListOf<HomeSectionItem>()
    seedItems.forEach { item ->
        val key =
            item.id?.takeIf { it.isNotBlank() }
                ?: item.perma_url?.takeIf { it.isNotBlank() }
                ?: listOf(item.type.orEmpty(), item.title.orEmpty(), item.subtitle.orEmpty()).joinToString("|")
        if (seen.add(key)) {
            deduped.add(item)
        }
    }
    return deduped
}
