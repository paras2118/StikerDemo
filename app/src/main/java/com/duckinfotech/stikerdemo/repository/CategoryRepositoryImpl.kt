package com.duckinfotech.stikerdemo.repository

import androidx.lifecycle.LiveData
import com.duckinfotech.stikerdemo.database.CategoryDao
import com.duckinfotech.stikerdemo.database.Result
import com.duckinfotech.stikerdemo.database.StickerCategorieAndPack
import com.duckinfotech.stikerdemo.database.StickerCategoryEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {
    override suspend fun getCategory(): Result<List<StickerCategoryEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(categoryDao.getCategory())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getCategoryFlow(): Result<Flow<List<StickerCategoryEntity>>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(categoryDao.getCategoryFlow())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getCategoryAndPackFlow(): Result<Flow<List<StickerCategorieAndPack>>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(categoryDao.getCategoryAndPackFlow())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override fun getCategoryLiveData(): Result<LiveData<List<StickerCategoryEntity>>> {
        return try {
            Result.Success(categoryDao.getCategoryLiveData())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getCategoryAndPackLiveData(): Result<LiveData<List<StickerCategorieAndPack>>> {
        return try {
            val list = categoryDao.getCategoryAndPackLiveData()
            if (list.value != null) {
                Result.Success(list)
            } else {
                Result.Error(throw NullPointerException("Empty list"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCategoryAndPack(): Result<List<StickerCategorieAndPack>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(categoryDao.getCategoryAndPack())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getCategoryAndPackById(catId: Int): Result<StickerCategorieAndPack> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(
                    categoryDao.getCategoryAndPackById(
                        catId
                    )
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}