package com.krishnatune.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krishnatune.data.home.HomeRepository
import com.krishnatune.models.HomeSectionItem
import com.krishnatune.models.ModuleConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

class HomeViewModel : ViewModel() {
    private val repository = HomeRepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val response = repository.getLaunchData()

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
}

private fun getItemsForSource(
    source: String?,
    data: com.krishnatune.models.HomeDataResponse
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
