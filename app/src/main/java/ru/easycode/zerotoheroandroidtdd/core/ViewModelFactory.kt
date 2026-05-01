package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ViewModelFactory : ProvideViewModel, ClearViewModel {

    class Base(private val provideViewModel: ProvideViewModel) : ViewModelFactory {
        private val cachedMap = mutableMapOf<Class<out ViewModel>, ViewModel>()

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
            return if (cachedMap.containsKey(viewModelClass)) {
                cachedMap[viewModelClass] as T
            } else {
                val viewModel = provideViewModel.viewModel(viewModelClass)
                cachedMap[viewModelClass] = viewModel
                viewModel
            }
        }

        override fun clear(viewModelClass: Class<out ViewModel>) {
            cachedMap.remove(viewModelClass)
        }
    }
}
