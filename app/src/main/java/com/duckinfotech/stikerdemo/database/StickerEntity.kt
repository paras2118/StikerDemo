package com.duckinfotech.stikerdemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duckinfotech.stikerdemo.domainModel.Sticker
import com.google.gson.annotations.SerializedName


@Entity
data class StickerEntity(
    @SerializedName("cat_id")
    val categoryId: Int,
    val packIdentifier: String,
    val imageFileName: String,
    val emojis: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


fun StickerEntity.asSticker(): Sticker {
    return Sticker(
        categoryId = this.categoryId,
        packIdentifier = this.packIdentifier,
        imageFileName = this.imageFileName,
        emojis = this.emojis
    )
}

fun List<StickerEntity>.asStickerList(): List<Sticker> {
    return map {
        Sticker(
            categoryId = it.categoryId,
            packIdentifier = it.packIdentifier,
            imageFileName = it.imageFileName,
            emojis = it.emojis
        )
    }
}