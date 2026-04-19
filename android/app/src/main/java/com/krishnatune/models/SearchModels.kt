package com.krishnatune.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("start") val start: Int? = null,
    @SerializedName("results") val results: List<SearchApiItem>? = null
)

data class SearchApiItem(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("perma_url") val permaUrl: String? = null,
    @SerializedName("more_info") val moreInfo: Map<String, Any>? = null
)

data class SearchUiItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: String,
    val image: String?,
    val permaUrl: String?
)

fun SearchApiItem.toUiItem(): SearchUiItem? {
    val normalizedType = type?.lowercase().orEmpty()
    if (title.isNullOrBlank() || normalizedType.isBlank()) return null

    return SearchUiItem(
        id = id ?: "$normalizedType-$title",
        title = title,
        subtitle = subtitle.orEmpty(),
        type = normalizedType,
        image = image,
        permaUrl = permaUrl
    )
}

data class AutocompleteResponse(
    @SerializedName("songs") val songs: AutocompleteSection? = null,
    @SerializedName("albums") val albums: AutocompleteSection? = null,
    @SerializedName("artists") val artists: AutocompleteSection? = null,
    @SerializedName("playlists") val playlists: AutocompleteSection? = null,
    @SerializedName("topquery") val topquery: AutocompleteSection? = null
)

data class AutocompleteSection(
    @SerializedName("data") val data: List<AutocompleteItem>? = null,
    @SerializedName("position") val position: Int? = null
)

data class AutocompleteItem(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("subtitle") val subtitle: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("perma_url") val permaUrl: String? = null,
    @SerializedName("description") val description: String? = null
)

fun AutocompleteItem.toUiItem(): SearchUiItem? {
    val itemTitle = title ?: return null
    val itemType = type?.lowercase() ?: "search"

    return SearchUiItem(
        id = id ?: "$itemType-$itemTitle",
        title = itemTitle,
        subtitle = subtitle ?: description ?: "",
        type = itemType,
        image = image,
        permaUrl = permaUrl
    )
}
