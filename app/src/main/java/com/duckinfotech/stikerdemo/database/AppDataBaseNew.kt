package com.duckinfotech.stikerdemo.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.duckinfotech.stikerdemo.appDefault.STICKERNEW_DATA_BASE_NAME
import com.duckinfotech.stikerdemo.workers.DatabaseWorkerNew

@Database(
    entities = [StickerCategoryEntity::class, StickerPackEntity::class, StickerEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBaseNew : RoomDatabase() {

    abstract fun stickerCategoryDao(): CategoryDao
    abstract fun stickerPackDao(): StickerPackDao
    abstract fun stickerDao(): StickerDao

    companion object {
        private var INSTANCE: AppDataBaseNew? = null
        fun getInstance() = INSTANCE!!
        fun init(context: Context): AppDataBaseNew {
            Log.d("AppDataBaseNew", "NewDatabase init call")
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildNewDatabasee(context.applicationContext).also { INSTANCE = it }
            }
        }

        //create and pre-populate database
        private fun buildNewDatabasee(context: Context): AppDataBaseNew {
            return Room.databaseBuilder(
                context,
                AppDataBaseNew::class.java,
                STICKERNEW_DATA_BASE_NAME
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("AppDataBaseNew", "onCreate")
                        val request = OneTimeWorkRequestBuilder<DatabaseWorkerNew>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("AppDataBaseNew", "onOpen new database")
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        Log.d("AppDataBaseNew", "onDestructiveMigration new database")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }

}