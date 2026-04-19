package com.krishnatune.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.krishnatune.data.search.SearchRepository
import com.krishnatune.db.AppDatabase
import com.krishnatune.models.SearchUiItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecentSearchUiItem(
    val query: String,
    val type: String,
)

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val results: List<SearchUiItem> = emptyList(),
    val errorMessage: String? = null,
    val recentSearches: List<RecentSearchUiItem> = emptyList(),
)

@OptIn(FlowPreview::class)
class SearchViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = SearchRepository(
        recentSearchDao = AppDatabase.getInstance(application).recentSearchDao()
    )

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeRecentSearches().collect { items ->
                _uiState.update { state ->
                    state.copy(
                        recentSearches = items.map { RecentSearchUiItem(it.query, it.type) }
                    )
                }
            }
        }

        viewModelScope.launch {
            _uiState
                .map { it.query.trim() }
                .debounce(350)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                results = emptyList(),
                                errorMessage = null
                            )
                        }
                    } else {
                        runSearch(query)
                    }
                }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                errorMessage = null
            )
        }
    }

    fun clearQuery() {
        _uiState.value = SearchUiState()
    }

    fun retry() {
        val query = _uiState.value.query.trim()
        if (query.isNotBlank()) {
            viewModelScope.launch {
                runSearch(query)
            }
        }
    }

    fun removeRecentSearch(query: String) {
        viewModelScope.launch {
            repository.removeRecentSearch(query)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            repository.clearRecentSearches()
        }
    }

    private suspend fun runSearch(query: String) {
        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        runCatching {
            repository.search(query)
        }.onSuccess { results ->
            repository.storeRecentSearch(
                query = query,
                type = results.firstOrNull()?.type ?: "search"
            )
            _uiState.update {
                it.copy(
                    isLoading = false,
                    results = results,
                    errorMessage = null
                )
            }
        }.onFailure { throwable ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    results = emptyList(),
                    errorMessage = throwable.message ?: "Search failed"
                )
            }
        }
    }
}
