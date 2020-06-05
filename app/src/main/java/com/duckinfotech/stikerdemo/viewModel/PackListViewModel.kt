package com.duckinfotech.stikerdemo.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.duckinfotech.stikerdemo.database.AppDataBaseNew
import com.duckinfotech.stikerdemo.database.Result
import com.duckinfotech.stikerdemo.database.StickerPackEntity
import com.duckinfotech.stikerdemo.database.asStickerPackList
import com.duckinfotech.stikerdemo.domainModel.StickerPack
import com.duckinfotech.stikerdemo.repository.PackDetailRepository
import com.duckinfotech.stikerdemo.repository.PackDetailRepositoryImpl
import kotlinx.coroutines.launch

class PackListViewModel : ViewModel() {
    private val _stickerPackList = MutableLiveData<StickerPackEntity>()
    private val _searchTerm = MutableLiveData<List<String>>()

    private val _searchList = MutableLiveData<List<StickerPackEntity>>()

    val searchList: LiveData<List<StickerPack>> = Transformations.map(_searchList) {
        it.asStickerPackList()
    }

    private val stickerPackDao = AppDataBaseNew.getInstance().stickerPackDao()
    private val stickerDao = AppDataBaseNew.getInstance().stickerDao()
    private var packDetailRepositoryImpl: PackDetailRepository

    init {
        packDetailRepositoryImpl = PackDetailRepositoryImpl.getInstance(
            stickerPackDao,
            stickerDao
        )
    }

    fun searchOnList(searchTerm: List<String>) {
        searchTerm.forEach {
            Log.d(TAG, it)
        }
        searchStickerPack(searchTerm)
    }

    private fun searchStickerPack(searchTerm: List<String>) {
        viewModelScope.launch {
            val searchList = packDetailRepositoryImpl.getStickerPackBySearch(searchTerm)
            if (searchList is Result.Success) {
                _searchList.value = searchList.data
            }
        }
    }

    companion object {
        const val TAG = "PackListViewModel"
    }
}