package com.duckinfotech.stikerdemo.domainModel

data class CategoriesModel(
    val id: Long,
    val searchTerm: String? = null,
    val title: String? = null,
    val items: List<StickerModel?>? = null
)
