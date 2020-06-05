package com.duckinfotech.stikerdemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CategoryDao : DaoBase<StickerCategoryEntity> {

    /**
     * Select all Category from StickerCategoryEntity table
     * @return list of all category
     * */
    @Query("SELECT * FROM StickerCategoryEntity")
    abstract suspend fun getCategory(): List<StickerCategoryEntity>

    /**
     * Select all category from StickerCategoryEntity table
     * @return list of all category warped in livedata
     * */
    @Query("SELECT * FROM StickerCategoryEntity")
    abstract fun getCategoryLiveData(): LiveData<List<StickerCategoryEntity>>

    @Transaction
    @Query("SELECT * FROM StickerCategoryEntity WHERE id IN (SELECT DISTINCT(categoryId) FROM StickerPackEntity)")
    abstract suspend fun getCategoryAndPack(): List<StickerCategorieAndPack>

    @Transaction
    @Query("SELECT * FROM StickerCategoryEntity WHERE id IN (SELECT DISTINCT(categoryId) FROM StickerPackEntity)")
    abstract fun getCategoryAndPackLiveData(): LiveData<List<StickerCategorieAndPack>>

    @Transaction
    @Query("SELECT * FROM StickerCategoryEntity WHERE id = :catId IN (SELECT DISTINCT(categoryId) FROM StickerPackEntity)")
    abstract suspend fun getCategoryAndPackById(catId: Int): StickerCategorieAndPack

    @Query("SELECT * FROM StickerCategoryEntity WHERE id == :catId")
    abstract suspend fun getCategoryById(catId: Int): StickerCategoryEntity

    /* Coroutine Flow */
    @Query("SELECT * FROM StickerCategoryEntity")
    abstract fun getCategoryFlow(): Flow<List<StickerCategoryEntity>>

    @Transaction
    @Query("SELECT * FROM StickerCategoryEntity WHERE id IN (SELECT DISTINCT(categoryId) FROM StickerPackEntity)")
    abstract fun getCategoryAndPackFlow(): Flow<List<StickerCategorieAndPack>>

}