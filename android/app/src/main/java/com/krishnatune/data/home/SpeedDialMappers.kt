package com.krishnatune.data.home

import com.krishnatune.db.entities.SpeedDialItemEntity
import com.krishnatune.models.HomeSectionItem

fun HomeSectionItem.toSpeedDialItemEntityOrNull(): SpeedDialItemEntity? {
    val resolvedId = id?.takeIf { it.isNotBlank() } ?: perma_url?.takeIf { it.isNotBlank() }
    if (resolvedId == null) {
        return null
    }

    return SpeedDialItemEntity(
        id = resolvedId,
        title = title ?: "Unknown",
        subtitle = subtitle,
        image = image,
        type = type ?: "song",
        permaUrl = perma_url,
    )
}

fun SpeedDialItemEntity.toHomeSectionItem(): HomeSectionItem {
    return HomeSectionItem(
        id = id,
        title = title,
        subtitle = subtitle,
        type = type,
        image = image,
        perma_url = permaUrl,
        language = null,
        more_info = null,
    )
}
