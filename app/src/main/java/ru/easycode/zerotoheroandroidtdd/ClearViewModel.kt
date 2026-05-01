package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.ViewModel

interface ClearViewModel {
    fun clearViewModel(clasz: Class<out ViewModel>)
}