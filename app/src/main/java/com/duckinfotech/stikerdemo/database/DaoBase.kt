package com.duckinfotech.stikerdemo.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface DaoBase<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listObj: List<T>)

    @Delete
    suspend fun delete(obj: T)
}