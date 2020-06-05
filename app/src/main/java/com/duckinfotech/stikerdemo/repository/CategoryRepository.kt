package com.duckinfotech.stikerdemo.repository

import androidx.lifecycle.LiveData
import com.duckinfotech.stikerdemo.database.Result
import com.duckinfotech.stikerdemo.database.StickerCategorieAndPack
import com.duckinfotech.stikerdemo.database.StickerCategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategory(): Result<List<StickerCategoryEntity>>
    fun getCategoryLiveData(): Result<LiveData<List<StickerCategoryEntity>>>
    fun getCategoryAndPackLiveData(): Result<LiveData<List<StickerCategorieAndPack>>>
    suspend fun getCategoryAndPack(): Result<List<StickerCategorieAndPack>>
    suspend fun getCategoryAndPackById(catId: Int): Result<StickerCategorieAndPack>

    /* Flow */
    suspend fun getCategoryFlow(): Result<Flow<List<StickerCategoryEntity>>>
    suspend fun getCategoryAndPackFlow(): Result<Flow<List<StickerCategorieAndPack>>>
}
