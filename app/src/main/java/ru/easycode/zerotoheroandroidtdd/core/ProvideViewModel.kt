package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T
}
