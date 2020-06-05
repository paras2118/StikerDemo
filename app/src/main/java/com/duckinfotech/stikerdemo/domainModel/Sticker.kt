package com.duckinfotech.stikerdemo.domainModel

import com.duckinfotech.stikerdemo.database.StickerEntity
import com.google.gson.annotations.SerializedName

data class Sticker(
    @SerializedName("cat_id")
    val categoryId: Int,
    val packIdentifier: String,
    val imageFileName: String,
    val emojis: String?
) {}

fun Sticker.asStickerEntity(): StickerEntity {
    return StickerEntity(
        categoryId = this.categoryId,
        packIdentifier = this.packIdentifier,
        imageFileName = this.imageFileName,
        emojis = this.emojis
    )
}

fun List<Sticker>.asStickerEntityList(): List<StickerEntity> {
    return map {
        StickerEntity(
            categoryId = it.categoryId,
            packIdentifier = it.packIdentifier,
            imageFileName = it.imageFileName,
            emojis = it.emojis
        )
    }
}