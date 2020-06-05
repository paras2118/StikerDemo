package com.duckinfotech.stikerdemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class StickerDao : DaoBase<StickerEntity> {
    @Query("SELECT * FROM StickerEntity WHERE packIdentifier = :packIdentifier")
    abstract suspend fun getStickerByIdentifier(packIdentifier: String): List<StickerEntity>

    @Query("SELECT * FROM StickerEntity WHERE packIdentifier = :packIdentifier")
    abstract fun getStickerByIdentifierOneShot(packIdentifier: String): List<StickerEntity>

    @Query("SELECT * FROM StickerEntity WHERE packIdentifier = :packIdentifier")
    abstract fun getStickerByIdentifierLiveData(packIdentifier: String): LiveData<List<StickerEntity>>
}