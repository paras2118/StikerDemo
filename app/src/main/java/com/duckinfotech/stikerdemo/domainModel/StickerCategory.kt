package com.duckinfotech.stikerdemo.domainModel

import com.google.gson.annotations.SerializedName

data class StickerCategory(
    val id: Int,
    @SerializedName("search_term")
    val searchTerm: String?,
    val title: String? = null,
    @SerializedName("items")
    val stickerPackList: List<StickerPack>
)
