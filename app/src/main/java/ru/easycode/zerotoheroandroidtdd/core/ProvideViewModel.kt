package ru.easycode.zerotoheroandroidtdd.core

import androidx.lifecycle.ViewModel

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(clasz: Class<T>): T

    class Factory(private val provide: ProvideViewModel) : ProvideViewModel, ClearViewModels {
        private val viewModels = mutableMapOf<Class<out ViewModel>, ViewModel>()

        override fun <T : ViewModel> viewModel(clasz: Class<T>): T {
            return if (viewModels.containsKey(clasz)) {
                viewModels[clasz] as T
            } else {
                val viewModel = provide.viewModel(clasz)
                viewModels[clasz] = viewModel
                viewModel
            }
        }

        override fun clear(vararg viewModelClasses: Class<out ViewModel>) {
            viewModelClasses.forEach {
                viewModels.remove(it)
            }
        }
    }
}

interface ClearViewModels {
    fun clear(vararg viewModelClasses: Class<out ViewModel>)
}
