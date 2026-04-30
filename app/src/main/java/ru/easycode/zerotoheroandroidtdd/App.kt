package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class App : Application() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = MainViewModel(
            repository = Repository.Base(),
            liveDataWrapper = LiveDataWrapper.Base()
        )
    }

    fun viewModel(owner: ViewModelStoreOwner): MainViewModel {
        return ViewModelProvider(owner, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        })[MainViewModel::class.java]
    }
}