package com.duckinfotech.stikerdemo.repository

import androidx.lifecycle.LiveData
import com.duckinfotech.stikerdemo.database.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PackDetailRepositoryImpl private constructor(
    private val stickerPackDao: StickerPackDao,
    private val stickerDao: StickerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PackDetailRepository {
    override fun getStickerPackLiveData(): Result<LiveData<List<StickerPackEntity>>> {
        return try {
            Result.Success(stickerPackDao.getStickerPackLiveData())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getStickerPack(): Result<List<StickerPackEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(stickerPackDao.getStickerPack())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerPackBySearch(searchTerm: List<String>): Result<List<StickerPackEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {

                val searchList = stickerPackDao.getStickerPackBySearch()
                    .filter { sTag ->
                        searchTerm.any {
                            sTag.name.contains(it, true)
                        }
                    }

                Result.Success(
                    searchList
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerPackAndSticker(): Result<List<StickerPackAndSticker>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(stickerPackDao.getStickerPackAndSticker())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerPackAndStickerByIdentifier(identifier: String): Result<StickerPackAndSticker> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(
                    stickerPackDao.getStickerPackAndStickerByIdentifier(
                        identifier
                    )
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerPackByIdentifier(identifier: String): Result<StickerPackEntity> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(
                    stickerPackDao.getStickerPackByIdentifier(
                        identifier
                    )
                )
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerByIdentifier(packIdentifier: String): Result<List<StickerEntity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val st = stickerDao.getStickerByIdentifier(packIdentifier)
                if (st.size <= 0) {
                    Result.Error(Exception("** No Data Found **"))
                } else {
                    Result.Success(st)
                }

            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getStickerByIdentifierLiveData(packIdentifier: String): Result<LiveData<List<StickerEntity>>> {
        return try {
            val st = stickerDao.getStickerByIdentifierLiveData(packIdentifier).value
            if (st?.size!! <= 0) {
                Result.Error(Exception("** No Data Found **"))
            } else {
                Result.Success(
                    stickerDao.getStickerByIdentifierLiveData(
                        packIdentifier
                    )
                )
            }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        @Volatile
        private var instance: PackDetailRepositoryImpl? = null
        fun getInstance(
            stickerPackDao: StickerPackDao,
            stickerDao: StickerDao
        ): PackDetailRepository {
            return instance ?: synchronized(this) {
                instance ?: PackDetailRepositoryImpl(
                    stickerPackDao,
                    stickerDao
                )
            }
        }

    }
}