package com.krishnatune.models

data class HomeDataResponse(
    val new_trending: List<HomeSectionItem>?,
    val top_playlists: List<HomeSectionItem>?,
    val new_albums: List<HomeSectionItem>?,
    val browse_discover: List<HomeSectionItem>?,
    val charts: List<HomeSectionItem>?,
    val radio: List<HomeSectionItem>?,
    val artist_recos: List<HomeSectionItem>?,
    val modules: Map<String, ModuleConfig>?
)

data class HomeSectionItem(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val type: String?,
    val image: String?,
    val perma_url: String?,
    val language: String?,
    val more_info: Map<String, Any>?
)

data class ModuleConfig(
    val title: String?,
    val subtitle: String?,
    val position: Int?,
    val source: String?,
    val type: String?
)
