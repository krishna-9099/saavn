package com.krishnatune.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krishnatune.api.SaavnApi
import com.krishnatune.models.HomeDataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: HomeDataResponse, val sortedModules: List<Pair<String, com.krishnatune.models.ModuleConfig>>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {
    private val api = SaavnApi.create()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val response = api.getLaunchData()
                
                // Sort modules by position to ensure dynamic ordering as per API
                val sortedModules = response.modules?.entries
                    ?.filter { it.value.position != null }
                    ?.sortedBy { it.value.position }
                    ?.map { Pair(it.key, it.value) } ?: emptyList()

                _uiState.value = HomeUiState.Success(response, sortedModules)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
