package com.duckinfotech.stikerdemo.utility

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.duckinfotech.stikerdemo.Network
import com.duckinfotech.stikerdemo.database.AppDataBaseNew
import com.duckinfotech.stikerdemo.database.StickerEntity
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

class ApiManagerImpl(
    val context: Context,
    val appDataBaseNew: AppDataBaseNew,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ApiManager {

    private val _stickerDownloadStatus = MutableLiveData<ApiManager.StickerDownloadStatus>()

    override val stickerDownloadStatus: LiveData<ApiManager.StickerDownloadStatus>
        get() = _stickerDownloadStatus

    override suspend fun downloadSticker(stickerPackUrl: String, catId: Int, identifier: String) =
        withContext(ioDispatcher) {

            Network.apiService.getStickerPack(stickerPackUrl)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        _stickerDownloadStatus.value = ApiManager.StickerDownloadStatus.FAILED
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        _stickerDownloadStatus.value = ApiManager.StickerDownloadStatus.SUCCESSFUL
                        writeResponseBodyToDisk(response, catId, identifier)
                    }

                })
        }

    private fun writeResponseBodyToDisk(
        response: Response<ResponseBody>,
        catId: Int,
        identifier: String
    ) {
        Log.i("MainActivity out", Thread.currentThread().name)
        CoroutineScope(Dispatchers.IO).launch {
            val fileLocationExract =
                File("${context.getExternalFilesDir(null)}/${catId}/${identifier}")
            fileLocationExract.mkdirs()
            var stickerParsedList = mutableListOf<StickerEntity>()
            Log.d("---", "writeResponseBodyToDisk")
            response.body()!!.byteStream().use { inputStream ->
                ZipInputStream(inputStream).use { zis ->
                    while (true) {
                        val zEntery = zis.nextEntry ?: break
                        Log.d("ZipUtility", zEntery.name)
                        FileOutputStream("${fileLocationExract}/${zEntery.name}").use { fos ->
                            while (true) {
                                val hh = zis.read()
                                if (hh == -1) {
                                    break
                                }
                                fos.write(hh)
                            }
                        }
                        appDataBaseNew.stickerDao().insert(
                            StickerEntity(
                                categoryId = catId,
                                packIdentifier = identifier,
                                imageFileName = "${fileLocationExract}/${zEntery.name}",
                                emojis = "qw"
                            )
                        )
                    }
                }
            }
        }

    }

    companion object {
        private var INSTANCE: ApiManagerImpl? = null
        fun getInstance() = INSTANCE
        fun init(context: Context, appDataBaseNew: AppDataBaseNew) {
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiManagerImpl(context.applicationContext, appDataBaseNew).also {
                    INSTANCE = it
                }
            }
        }
    }
}