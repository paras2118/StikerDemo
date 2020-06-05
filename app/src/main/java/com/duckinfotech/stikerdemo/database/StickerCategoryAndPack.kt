package com.duckinfotech.stikerdemo.database

import androidx.room.Embedded
import androidx.room.Relation
import com.duckinfotech.stikerdemo.domainModel.StickerCategory

data class StickerCategorieAndPack(
    @Embedded val stickerCategoryEntity: StickerCategoryEntity,
    @Relation(parentColumn = "id", entityColumn = "categoryId")
    val stickerPackEntity: List<StickerPackEntity>
)

fun List<StickerCategorieAndPack>.asStickerCategory(): List<StickerCategory> {
    return map {
        StickerCategory(
            id = it.stickerCategoryEntity.id,
            searchTerm = it.stickerCategoryEntity.searchTerm,
            title = it.stickerCategoryEntity.title,
            stickerPackList = it.stickerPackEntity.asStickerPackList()
        )
    }
}