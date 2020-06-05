package com.duckinfotech.stikerdemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class StickerPackDao : DaoBase<StickerPackEntity> {

    @Query("SELECT * FROM StickerPackEntity")
    abstract suspend fun getStickerPack(): List<StickerPackEntity>

    @Query("SELECT * FROM StickerPackEntity")
    abstract fun getStickerPackLiveData(): LiveData<List<StickerPackEntity>>

    @Query("SELECT * FROM StickerPackEntity")
    abstract suspend fun getStickerPackBySearch(): List<StickerPackEntity>

    @Transaction
    @Query("SELECT * FROM StickerPackEntity WHERE identifier IN(SELECT DISTINCT(packIdentifier) FROM StickerEntity)")
    abstract suspend fun getStickerPackAndSticker(): List<StickerPackAndSticker>

    @Transaction
    @Query("SELECT * FROM StickerPackEntity WHERE identifier = :identifier IN(SELECT DISTINCT(packIdentifier) FROM StickerEntity)")
    abstract suspend fun getStickerPackAndStickerByIdentifier(identifier: String): StickerPackAndSticker

    @Query("SELECT * FROM StickerPackEntity WHERE identifier = :identifier")
    abstract suspend fun getStickerPackByIdentifier(identifier: String): StickerPackEntity

    @Query("SELECT * FROM StickerPackEntity WHERE identifier = :identifier")
    abstract fun getStickerPackByIdentifierOneShot(identifier: String): List<StickerPackEntity>


}