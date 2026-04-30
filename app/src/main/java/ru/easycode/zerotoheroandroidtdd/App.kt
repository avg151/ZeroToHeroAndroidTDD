package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class App : Application(), ProvideViewModel {

    private lateinit var container: DependencyContainer

    override fun onCreate() {
        super.onCreate()
        container = DependencyContainer.Base()
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, container)[clazz]
    }
}
