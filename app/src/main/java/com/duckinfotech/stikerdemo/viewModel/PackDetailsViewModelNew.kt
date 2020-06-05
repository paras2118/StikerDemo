package com.duckinfotech.stikerdemo.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.duckinfotech.stikerdemo.database.*
import com.duckinfotech.stikerdemo.domainModel.Sticker
import com.duckinfotech.stikerdemo.domainModel.StickerPack
import com.duckinfotech.stikerdemo.repository.PackDetailRepository
import com.duckinfotech.stikerdemo.repository.PackDetailRepositoryImpl
import com.duckinfotech.stikerdemo.utility.ApiManagerImpl
import kotlinx.coroutines.launch

class PackDetailsViewModelNew(val identifier: String, val catId: Int) : ViewModel() {

    private val _packAndSticker = MutableLiveData<StickerPackAndSticker>()
    private val _stickerPack = MutableLiveData<StickerPackEntity>()
    private val _sticker = MutableLiveData<List<StickerEntity>>().also { it.value = emptyList() }

    val packAndSticker: LiveData<StickerPack> = Transformations.map(_packAndSticker) {
        it.asStickerPack()
    }

    val stickerPack: LiveData<StickerPack> = Transformations.map(_stickerPack) {
        it.asStickerPack()
    }


    private val stickerPackDao = AppDataBaseNew.getInstance().stickerPackDao()
    private val stickerDao = AppDataBaseNew.getInstance().stickerDao()
    private var packDetailRepositoryImpl: PackDetailRepository

    init {
        packDetailRepositoryImpl =
            PackDetailRepositoryImpl.getInstance(
                stickerPackDao,
                stickerDao
            )
        loadStickerPackData()
    }

    val stickers: LiveData<List<Sticker>> =
        Transformations.map(stickerDao.getStickerByIdentifierLiveData(identifier)) {
            it.asStickerList()
        }

    private fun loadStickerPackData() {
        viewModelScope.launch {

            val stickerPack = packDetailRepositoryImpl.getStickerPackByIdentifier(identifier)
            if (stickerPack is Result.Success) {
                _stickerPack.value = stickerPack.data
            }

            when (val sticker = packDetailRepositoryImpl.getStickerByIdentifier(identifier)) {
                is Result.Success -> {
                    // _sticker.value = sticker.data
                }
                is Result.Error -> {
                    val d = _stickerPack.value!!.packUrl!!.split("/").takeLast(2)
                    ApiManagerImpl.getInstance()!!.downloadSticker(
                        stickerPackUrl = "${d.get(0)}/${d.get(1)}",
                        catId = catId,
                        identifier = identifier
                    )
                    Log.d("--------", sticker.exception.toString())

                }
            }
        }
    }

}