package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class App : Application() {

    private lateinit var factory: MainViewModelFactory

    override fun onCreate() {
        super.onCreate()
        factory = MainViewModelFactory(
            repository = Repository.Base(),
            liveDataWrapper = LiveDataWrapper.Base()
        )
    }

    fun provideMainViewModel(owner: ViewModelStoreOwner): MainViewModel {
        return ViewModelProvider(owner, factory)[MainViewModel::class.java]
    }
}

class MainViewModelFactory(
    private val repository: Repository,
    private val liveDataWrapper: LiveDataWrapper
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, liveDataWrapper) as T
    }
}
