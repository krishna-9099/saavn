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
