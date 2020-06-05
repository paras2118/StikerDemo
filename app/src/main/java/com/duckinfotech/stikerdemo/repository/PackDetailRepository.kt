package com.duckinfotech.stikerdemo.repository

import androidx.lifecycle.LiveData
import com.duckinfotech.stikerdemo.database.Result
import com.duckinfotech.stikerdemo.database.StickerEntity
import com.duckinfotech.stikerdemo.database.StickerPackAndSticker
import com.duckinfotech.stikerdemo.database.StickerPackEntity

interface PackDetailRepository {
    fun getStickerPackLiveData(): Result<LiveData<List<StickerPackEntity>>>
    suspend fun getStickerPack(): Result<List<StickerPackEntity>>
    suspend fun getStickerPackBySearch(searchTerm: List<String>): Result<List<StickerPackEntity>>
    suspend fun getStickerPackAndSticker(): Result<List<StickerPackAndSticker>>
    suspend fun getStickerPackAndStickerByIdentifier(identifier: String): Result<StickerPackAndSticker>
    suspend fun getStickerPackByIdentifier(identifier: String): Result<StickerPackEntity>

    suspend fun getStickerByIdentifier(packIdentifier: String): Result<List<StickerEntity>>
    suspend fun getStickerByIdentifierLiveData(packIdentifier: String): Result<LiveData<List<StickerEntity>>>
}