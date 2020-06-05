package com.duckinfotech.stikerdemo.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class StickerCategoryEntity(
    @PrimaryKey
    val id: Int,
    @SerializedName("search_term")
    val searchTerm: String?,
    val title: String? = null
) {
    @Ignore
    val items: List<StickerPackEntity>? = null
}
