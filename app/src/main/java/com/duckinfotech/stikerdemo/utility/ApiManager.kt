package com.duckinfotech.stikerdemo.utility

import androidx.lifecycle.LiveData

interface ApiManager {

    //
    val stickerDownloadStatus: LiveData<StickerDownloadStatus>

    suspend fun downloadSticker(stickerPackUrl: String, catId: Int, identifier: String)

    enum class StickerDownloadStatus {
        STARTED, FAILED, SUCCESSFUL
    }
}