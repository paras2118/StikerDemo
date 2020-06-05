package com.duckinfotech.stikerdemo.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.duckinfotech.stikerdemo.database.AppDataBaseNew
import com.duckinfotech.stikerdemo.database.ResponceTemp
import com.duckinfotech.stikerdemo.database.StickerPackEntity
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import java.io.Reader

class DatabaseWorkerNew(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("sticker.json").use { inputStream ->
                JsonReader(inputStream.reader() as Reader?).use { jsonReader ->
                    val categories: ResponceTemp =
                        Gson().fromJson(jsonReader, ResponceTemp::class.java)
                    Log.i(TAG, "------------------------------------")
                    Log.i(
                        TAG,
                        "------------------${categories.categoriesList.size}------------------"
                    )
                    val stickerPackList: MutableList<StickerPackEntity> = mutableListOf()
                    categories.categoriesList.forEach { cat ->
                        cat.items!!.forEach {
                            stickerPackList.add(it)
                        }
                    }

                    val appDataBaseNew = AppDataBaseNew.getInstance()
                    appDataBaseNew.stickerCategoryDao().insertAll(categories.categoriesList)
                    appDataBaseNew.stickerPackDao().insertAll(stickerPackList)


                    /*    val categories: ResponceData =
                            Gson().fromJson(jsonReader, ResponceData::class.java)
                        Log.i(TAG, "------------------------------------")
                        val stickerList: MutableList<Sticker> = mutableListOf()
                        val listType = object : TypeToken<Array<String?>?>() {}.type
                        categories.categoriesList.forEach { cat ->
                            cat.items!!.forEach {
                                stickerList.add(it!!)
                            }
                        }
                        val appDatabase = AppDataBase.getInstance()
                        appDatabase.categoriesDao().insertAll(categories.categoriesList)
                        appDatabase.stickerDao().insertAll(stickerList)*/
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = DatabaseWorkerNew::class.java.simpleName
    }
}