package com.duckinfotech.stikerdemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PackDetailsVieModelFactoryNew(val identifier: String, val catId: Int) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PackDetailsViewModelNew(identifier, catId) as T
    }
}