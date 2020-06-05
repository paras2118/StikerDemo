package com.duckinfotech.stikerdemo.database

import com.google.gson.annotations.SerializedName

data class ResponceTemp(
    @SerializedName("categories")
    val categoriesList: List<StickerCategoryEntity>
) {
}