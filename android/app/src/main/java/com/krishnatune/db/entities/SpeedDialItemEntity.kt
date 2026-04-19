package com.krishnatune.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speed_dial_item")
data class SpeedDialItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val type: String,
    val permaUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
)
