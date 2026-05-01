package ru.easycode.zerotoheroandroidtdd

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers

class TaskApp : Application() {

    lateinit var database: ItemsDataBase
    private val viewModels = mutableMapOf<Class<out ViewModel>, ViewModel>()

    val liveDataWrapper = ListLiveDataWrapper.Base()
    val repository by lazy {
        Repository.Base(database.itemsDao(), Now.Base())
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            ItemsDataBase::class.java,
            "items_database"
        ).allowMainThreadQueries().build()
    }

    fun <T : ViewModel> getViewModel(clasz: Class<T>, clear: ClearViewModel): T {
        if (viewModels.containsKey(clasz)) return viewModels[clasz] as T
        val viewModel = when (clasz) {
            MainViewModel::class.java -> MainViewModel(
                repository, liveDataWrapper, Dispatchers.IO, Dispatchers.Main
            )

            AddViewModel::class.java -> AddViewModel(
                repository, liveDataWrapper, clear, Dispatchers.IO, Dispatchers.Main
            )

            else -> throw IllegalStateException("Unknown view model $clasz")
        }
        viewModels[clasz] = viewModel
        return viewModel as T
    }

    fun clear(clasz: Class<out ViewModel>) {
        viewModels.remove(clasz)
    }
}